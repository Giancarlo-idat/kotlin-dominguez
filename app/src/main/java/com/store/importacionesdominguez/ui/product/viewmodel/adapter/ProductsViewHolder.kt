package com.store.importacionesdominguez.ui.product.viewmodel.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.databinding.ItemProductsBinding
import java.math.RoundingMode

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProductsBinding.bind(view)

    fun render(products: ProductModel, onClickListener: (String) -> Unit) {

        Glide.with(binding.imageViewProduct.context)
            .load(products.imagen)
            .into(binding.imageViewProduct)


        binding.txtProductBrand.text = products.marca
        binding.txtProductModel.text = products.modelo
        binding.txtProductPrice.text = "S/ " + products.precio.setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.root.setOnClickListener {
            products.id?.let { id -> onClickListener(id) }
        }
    }
}