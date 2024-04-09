package com.store.importacionesdominguez.ui.product.viewmodel.adapter

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.ItemProductsBinding
import com.store.importacionesdominguez.utils.preferences.CartManager
import java.math.RoundingMode

class ProductsViewHolder(private val binding: ItemProductsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductModel, onClickListener: (String) -> Unit) {
        // Cargar la imagen del producto usando Glide
        Glide.with(binding.imageViewProduct.context)
            .load(product.imagen)
            .into(binding.imageViewProduct)

        binding.txtProductBrand.text = product.marca

        binding.txtProductModel.text = product.modelo

        val formattedPrice = "S/ ${product.precio.setScale(2, RoundingMode.HALF_EVEN)}"
        binding.txtProductPrice.text = formattedPrice

        binding.root.setOnClickListener {
            product.id?.let { id -> onClickListener(id) }
        }

        binding.llAddToCart.setOnClickListener {
            val context = binding.root.context
            val itemToAdd = ShoppingCartModel(
                id = product.id ?: "",
                modelo = product.modelo ?: "",
                precio = product.precio,
                cantidad = 1,
                products = product,
                marca = product.marca ?: "",
                imagen = product.imagen ?: ""
            )

            CartManager.addProduct(context, itemToAdd)
            Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
        }
    }


}