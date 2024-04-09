package com.store.importacionesdominguez.ui.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.data.model.CategoriaModel
import com.store.importacionesdominguez.databinding.ItemsCategoriesBinding

class CategoriesAdapter(
    private val onClickListener: (String) -> Unit
) :RecyclerView.Adapter<CategoriesViewHolder>(){

    private var categoriesList : MutableList<CategoriaModel> = mutableListOf()


    fun setData(categories: List<CategoriaModel>) {
        categoriesList.clear()
        categoriesList.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemsCategoriesBinding.inflate(layoutInflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int = categoriesList.size
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categories : CategoriaModel = categoriesList[position]
        holder.bind(categories, onClickListener)
    }
}