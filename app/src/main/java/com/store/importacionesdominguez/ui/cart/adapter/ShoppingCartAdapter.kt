package com.store.importacionesdominguez.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.ItemsShoppingCartBinding


class ShoppingCartAdapter (
    private var cartItems: List<ShoppingCartModel>,
    private val onDeleteClickListener: (ShoppingCartModel) -> Unit,
    private val onIncrementClickListener: (ShoppingCartModel) -> Unit,
    private val onDecrementClickListener: (ShoppingCartModel) -> Unit
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemsShoppingCartBinding.inflate(layoutInflater, parent, false)
        return ShoppingCartViewHolder(binding)
    }

    fun updateCartItems(newCartItems: MutableList<ShoppingCartModel>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val cartItems = cartItems[position]
        holder.bind(cartItems, onDeleteClickListener, onIncrementClickListener, onDecrementClickListener)
    }

}