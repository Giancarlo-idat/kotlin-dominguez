package com.store.importacionesdominguez.ui.product.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.providers.ProductsProvider
import com.store.importacionesdominguez.databinding.FragmentProductDetailBinding
import com.store.importacionesdominguez.ui.product.viewmodel.adapter.FragmentPageAdapter
import java.math.RoundingMode


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        setupTab()
        displayProducts()
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

    private fun displayProducts() {
        val productId = args.productId
        val products = ProductsProvider.getProductById(productId)
        products?.let { product ->
            binding.txtTitleModel.text = product.modelo
            binding.txtTitleBrandModel.text = product.marca
            Glide.with(requireContext()).load(product.imagen).into(binding.imgProduct)
            binding.txtPrice.text = "S/. " + product.precio.setScale(2, RoundingMode.HALF_EVEN).toString()

            if (childFragmentManager.findFragmentByTag("DescriptionFragment") == null) {
                val descriptionFragment = ProductDescriptionFragment()

                val bundle = Bundle()
                bundle.putString("description", product.descripcion)
                descriptionFragment.arguments = bundle

                childFragmentManager.beginTransaction()
                    .add(R.id.descriptionContainer, descriptionFragment, "DescriptionFragment")
                    .commit()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
