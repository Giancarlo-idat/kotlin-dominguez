package com.store.importacionesdominguez.ui.product.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.ui.product.viewmodel.adapter.ProductsViewHolder

class ProductsAdapter(
    private val productsList: List<ProductModel>,
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<ProductsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(layoutInflater.inflate(R.layout.item_products, parent, false))

    }

    override fun getItemCount(): Int = productsList.size


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products: ProductModel = productsList[position]
        holder.render(products, onClickListener)
    }


}