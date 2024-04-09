package com.store.importacionesdominguez.ui.product.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.databinding.ItemProductsBinding

class ProductsAdapter(
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<ProductsViewHolder>() {

    private var productsList: MutableList<ProductModel> = mutableListOf()

    fun setData(products: List<ProductModel>) {
        productsList.clear()
        productsList.addAll(products)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductsBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(binding)

    }

    override fun getItemCount(): Int = productsList.size


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products: ProductModel = productsList[position]
        holder.bind(products, onClickListener)
    }


}