package com.store.importacionesdominguez.ui.favorites.adapter

import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.importacionesdominguez.data.model.FavoritesModel
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.ItemsFavoriteBinding
import com.store.importacionesdominguez.utils.preferences.CartManager
import java.math.RoundingMode

class FavoritesViewHolder(
    private val binding: ItemsFavoriteBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: FavoritesModel,
        onDeleteClickListener: (FavoritesModel) -> Unit,
        onClickListener: (String) -> Unit
    ) {

        binding.apply {
            binding.txtMarca.text = item.marca
            binding.txtModelo.text = item.modelo
            val formattedPrice = "S/ ${item.precio.setScale(2, RoundingMode.HALF_EVEN)}"
            binding.txtPrice.text = formattedPrice
            loadProductImage(item.imagen, binding.imgProduct)

            // Set click listeners
            btnImgDelete.setOnClickListener { onDeleteClickListener(item) }

            binding.btnImgCart.setOnClickListener {
                val context = binding.root.context
                val itemToAdd = ShoppingCartModel(
                    id = item.id ?: "",
                    modelo = item.modelo ?: "",
                    precio = item.precio,
                    cantidad = 1,
                    products = item.products,
                    marca = item.marca ?: "",
                    imagen = item.imagen ?: ""
                )

                CartManager.addProduct(context, itemToAdd)
                Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()

            }

            binding.root.setOnClickListener { onClickListener(item.id)
            }
        }
    }


    private fun loadProductImage(imageUrl: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
    }
}