package com.store.importacionesdominguez.ui.product.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.tabs.TabLayout
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.FavoritesModel
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.FragmentProductDetailBinding
import com.store.importacionesdominguez.ui.product.viewmodel.ProductsViewModel
import com.store.importacionesdominguez.ui.product.viewmodel.adapter.FragmentPageAdapter
import com.store.importacionesdominguez.utils.preferences.CartManager
import com.store.importacionesdominguez.utils.preferences.FavoritesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.math.RoundingMode

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter
    private val viewModelProducts: ProductsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        setupTab()
        displayProducts()
        onBackPressed()
    }

    private fun initVariables() {
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        adapter = FragmentPageAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter
    }

    private fun setupTab() {
        tabLayout.addTab(tabLayout.newTab().setText("Descripción"))
        tabLayout.addTab(tabLayout.newTab().setText("Especificaciones técnicas"))

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayProducts() {

        val productId = args.productId

        // Obtener el producto de forma asíncrona
        viewModelProducts.getProductById(productId)

        // Observar el flujo del producto y actualizar la UI cuando se obtenga
        viewLifecycleOwner.lifecycleScope.launch {
            viewModelProducts.product.collect { productResponse ->
                if (productResponse.isSuccessful) {
                    val productResponseBody = productResponse.body()
                    println("Product Detail: $productResponseBody")
                    productResponseBody?.let { product ->
                        // Actualizar la UI con los detalles del producto
                        binding.txtTitleModel.text = product.modelo
                        binding.txtTitleBrandModel.text = product.marca
                        Glide.with(requireContext()).load(product.imagen).into(binding.imgProduct)
                        binding.txtPrice.text =
                            "S/. " + product.precio.setScale(2, RoundingMode.HALF_EVEN).toString()
                        println("Description: ${product.descripcion}")


                        // Crear un nuevo objeto ShoppingCartModel con los datos del producto
                        val shoppingCartProduct = ShoppingCartModel(
                            id = productId,
                            modelo = product.modelo,
                            marca = product.marca,
                            precio = product.precio,
                            cantidad = 1,
                            products = product,
                            imagen = product.imagen
                        )

                        val favoritesProduct = FavoritesModel(
                            id = productId,
                            modelo = product.modelo,
                            marca = product.marca,
                            cantidad = 1,
                            precio = product.precio,
                            products = product,
                            imagen = product.imagen
                        )

                        //Agregar al carrito
                        binding.llAddToCart.setOnClickListener {
                            addToCart(shoppingCartProduct)
                        }

                        binding.btnfavorites.setOnClickListener {
                            addFavorites(favoritesProduct)
                        }

                        // Verificar si la descripción del producto no está vacía
                        val descriptionContainer = binding.descriptionContainer
                        val technicalSheetContainer = binding.technicalSheetContainer

                        if (product.descripcion.isNotEmpty()) {
                            // Mostrar fragmento de descripción
                            descriptionContainer.visibility = View.VISIBLE
                            technicalSheetContainer.visibility = View.GONE

                            if (childFragmentManager.findFragmentByTag("DescriptionFragment") == null) {
                                val descriptionFragment = ProductDescriptionFragment()
                                val bundle = Bundle()
                                bundle.putString("description", product.descripcion)
                                descriptionFragment.arguments = bundle

                                childFragmentManager.beginTransaction().add(
                                    R.id.descriptionContainer,
                                    descriptionFragment,
                                    "DescriptionFragment"
                                ).commit()
                            }
                        } else if (product.fichaTecnica.isNotEmpty()) {
                            // Mostrar fragmento de ficha técnica
                            descriptionContainer.visibility = View.GONE
                            technicalSheetContainer.visibility = View.VISIBLE

                            if (childFragmentManager.findFragmentByTag("TechnicalSheetFragment") == null) {
                                val technicalSheetFragment = ProductSpecificationsFragment()
                                val jsonFichaTecnica =
                                    ObjectMapper().writeValueAsString(product.fichaTecnica)

                                val bundle = Bundle()
                                bundle.putString("technicalSheet", jsonFichaTecnica)
                                technicalSheetFragment.arguments = bundle

                                childFragmentManager.beginTransaction().add(
                                    R.id.technicalSheetContainer,
                                    technicalSheetFragment,
                                    "TechnicalSheetFragment"
                                ).commit()
                            }
                        }


                    }
                } else {
                    // Manejar el caso en que no se pueda obtener el producto
                    val errorMessage = productResponse.errorBody()?.toString()
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun addToCart(product: ShoppingCartModel) {
        val context = requireContext()
        CartManager.addProduct(context, product)
        Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
    }


    private fun addFavorites(product: FavoritesModel) {
        val context = requireContext()
        // Si el producto ya fue guardado en favoritos, mostrar un mensaje de error
        if (FavoritesManager.getFavorites(context).contains(product)) {
            Toast.makeText(context, "El producto ya fue añadido a favoritos", Toast.LENGTH_SHORT)
                .show()
        } else {
            FavoritesManager.addFavorite(context, product)
            Toast.makeText(context, "Producto añadido a favoritos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onBackPressed() {
        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
