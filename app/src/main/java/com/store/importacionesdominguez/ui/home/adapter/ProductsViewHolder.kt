package com.store.importacionesdominguez.ui.home.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.databinding.ItemProductsBinding

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProductsBinding.bind(view)

    fun render(products: ProductModel, onClickListener: (String) -> Unit) {

        Glide.with(binding.imageViewProduct.context)
            .load(products.image)
            .into(binding.imageViewProduct)


        binding.txtProductBrand.text = products.marca
        binding.txtProductModel.text = products.model
        binding.txtProductPrice.text = products.price.toString()

        binding.root.setOnClickListener {
            onClickListener(products.id)
        }
    }
}