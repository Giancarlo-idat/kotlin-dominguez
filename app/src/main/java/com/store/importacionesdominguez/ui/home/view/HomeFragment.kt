package com.store.importacionesdominguez.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.databinding.FragmentHomeBinding
import com.store.importacionesdominguez.ui.categories.adapter.CategoriesAdapter
import com.store.importacionesdominguez.ui.categories.viewmodel.CategoriesViewModel
import com.store.importacionesdominguez.ui.home.adapter.BannerAdapter
import com.store.importacionesdominguez.ui.product.viewmodel.ProductsViewModel
import com.store.importacionesdominguez.ui.product.viewmodel.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: BannerAdapter

    private val viewModelProducts: ProductsViewModel by viewModels()
    private var productsAdapter = ProductsAdapter { productId ->
        navigateToProductDetail(productId)
    }
    private val viewModelCategories: CategoriesViewModel by viewModels()
    private var categoriesAdapter = CategoriesAdapter { categoryId ->
        // Navegar a la pantalla de productos
        navigateCategoryDetail(categoryId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initViewPager2()
        setupTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
        initRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProducts()
        getCategories()
        setupSearchTextWatcher()
        getProductsByCategory()
    }

    private fun initViewPager2() {
        viewPager2 = binding.viewPager2
        imageList = ArrayList()
        handler = Handler(Looper.myLooper()!!)
        val image1 = R.drawable.asus
        val image2 = R.drawable.banner

        imageList.add(image1)
        imageList.add(image2)

        adapter = BannerAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
    }

    private fun setupTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewProducts
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = productsAdapter

        val recyclerViewCategories = binding.recyclerViewCategories
        recyclerViewCategories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategories.adapter = categoriesAdapter
    }

    private fun navigateToProductDetail(productId: String) {
        val product = fetchProductById(productId)
        product.let {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productId)
            findNavController().navigate(action)
        }
    }

    private fun getProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModelProducts.productos.collect { productListResponse ->
                if (productListResponse.isSuccessful) {
                    val productList = productListResponse.body()
                    productList?.let {
                        productsAdapter.setData(it)
                    }
                } else {
                    val errorMessage = productListResponse.errorBody()?.toString()
                    // Manejar el error
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModelProducts.getProducts()
    }

    private fun fetchProductById(productId: String) {
        viewModelProducts.getProductById(productId)
    }

    private fun navigateCategoryDetail(categoryId: String) {
        val category = fetchCategoryById(categoryId)
        category.let {
            // Navegar a la pantalla de detalle de categoría
        }
    }

    private fun getCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModelCategories.categories.collect { categoriesResponse ->
                if (categoriesResponse.isSuccessful) {
                    val categoriesList = categoriesResponse.body()
                    categoriesList?.let {
                        categoriesAdapter.setData(it)
                    }
                } else {
                    categoriesResponse.errorBody()?.let {
                        // Manejar error
                        val errorMessage = categoriesResponse.errorBody().toString()
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewModelCategories.getCategories()
    }

    private fun fetchCategoryById(productId: String) {
        viewModelCategories.getCategoryById(productId)
    }

    private fun setupSearchTextWatcher() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus() // Esto quita el foco del SearchView
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Aquí puedes realizar alguna acción cuando el texto de búsqueda cambia
                if (newText != null) {
                    if (newText.isEmpty()) {
                        getProducts()
                    } else {
                        viewModelProducts.getProductsByModel(newText)
                    }
                }
                return true
            }
        })

    }

    private fun getProductsByCategory() {
        val onClickListener: (String) -> Unit = { categoryId ->
            viewModelProducts.getProductsByCategory(categoryId)
        }
        categoriesAdapter = CategoriesAdapter(onClickListener)
        binding.recyclerViewCategories.adapter = categoriesAdapter
        Log.d("HomeFragment", "getProductsByCategory $categoriesAdapter")


        binding.tvCategories.setOnClickListener {
            viewModelProducts.getProducts()
        }

    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}