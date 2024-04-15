package com.store.importacionesdominguez.ui.cart.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.ItemsShoppingCartBinding

class ShoppingCartViewHolder(
    private val binding: ItemsShoppingCartBinding
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(
        item: ShoppingCartModel,
        onDeleteClickListener: (ShoppingCartModel) -> Unit,
        onIncrementClickListener: (ShoppingCartModel) -> Unit,
        onDecrementClickListener: (ShoppingCartModel) -> Unit
    ) {
        binding.apply{
            binding.tvProductName.text = item.modelo
            binding.tvMarca.text = item.marca
            binding.tvPrice.text = String.format("S/. %.2f", item.precio)
            binding.tvQuantity.text = item.cantidad.toString()
            loadProductImage(item.imagen, binding.ivProductImage)

            // Set click listeners
            btnRemove.setOnClickListener { onDeleteClickListener(item) }
            btnIncrement.setOnClickListener { onIncrementClickListener(item) }
            btnDecrement.setOnClickListener { onDecrementClickListener(item) }
        }
    }

    private fun loadProductImage(imageUrl: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
    }

}