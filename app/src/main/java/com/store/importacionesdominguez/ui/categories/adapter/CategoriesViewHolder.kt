package com.store.importacionesdominguez.ui.categories.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.store.importacionesdominguez.data.model.CategoriaModel
import com.store.importacionesdominguez.databinding.ItemsCategoriesBinding

class CategoriesViewHolder(
    private val binding: ItemsCategoriesBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(categories: CategoriaModel, onClickListener: (String) -> Unit) {
        binding.tvCategoriesName.text = categories.nombre

        Glide.with(binding.ivCategoriesImage.context)
            .load(categories.imagen)
            .into(binding.ivCategoriesImage)

        binding.root.setOnClickListener {
            onClickListener(categories.id)
        }

        binding.ivCategoriesImage.setOnClickListener {
            onClickListener(categories.nombre)
        }
    }
}