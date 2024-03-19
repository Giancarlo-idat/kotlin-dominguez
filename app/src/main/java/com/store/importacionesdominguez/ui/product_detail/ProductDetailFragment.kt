package com.store.importacionesdominguez.ui.product_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.store.importacionesdominguez.data.providers.ProductsProvider
import com.store.importacionesdominguez.databinding.FragmentProductDetailBinding


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    val args: ProductDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        displayProducts()
    }

    private fun initView() {

        /* Configuración de los TextViews del Fragment */
        val description = binding.txtDescription
        val specification = binding.txtSpecifications
        val reviews = binding.txtReviews

        /* Configuración de los tabs del Fragment */
        val tabOverview = binding.txtTabTitleOverview
        val tabSpecification = binding.txtTabTitleSpecifications
        val tabReviews = binding.txtTabTitleReviews


        binding.llTabs.visibility = View.VISIBLE

        /* Configuración de clicks */

     /*   tabOverview.setOnClickListener { toggleVisibility(description) }
        tabSpecification.setOnClickListener { toggleVisibility(specification) }
        tabReviews.setOnClickListener { toggleVisibility(reviews) }*/


    }

    private fun displayProducts() {
        val productId = args.productId
        val products = ProductsProvider.getProductById(productId)
        products?.let { product ->
            binding.txtTitleModel.text = product.model
            binding.txtTitleBrandModel.text = product.marca
            Glide.with(requireContext()).load(product.image).into(binding.imgProduct)
            binding.txtPrice.text = product.price
            binding.txtDescription.text = product.description
        }

    }

    private fun toggleVisibility(view: View) {
        if (view.visibility == View.VISIBLE) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

}