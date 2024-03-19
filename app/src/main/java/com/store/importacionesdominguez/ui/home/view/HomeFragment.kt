package com.store.importacionesdominguez.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.data.providers.ProductsProvider
import com.store.importacionesdominguez.databinding.FragmentHomeBinding
import com.store.importacionesdominguez.ui.home.adapter.BannerAdapter
import com.store.importacionesdominguez.ui.home.adapter.ProductsAdapter
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: BannerAdapter

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

    private fun initViewPager2() {
        viewPager2 = binding.viewPager2
        imageList = ArrayList()
        handler = Handler(Looper.myLooper()!!)
        var image1 = R.drawable.asus
        var image2 = R.drawable.banner

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
        val transformer = CompositePageTransformer();
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
        val productsAdapter = ProductsAdapter(ProductsProvider.productList) { productId ->
            navigateToProductDetail(productId)
        }
        recyclerView.adapter = productsAdapter
    }

    private fun navigateToProductDetail(productId: String) {
        val product = getProductById(productId)
        product?.let {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productId)
            findNavController().navigate(action)
        }
    }

    private fun getProductById(productId: String): ProductModel? {
        return ProductsProvider.productList.find { it.id == productId }
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