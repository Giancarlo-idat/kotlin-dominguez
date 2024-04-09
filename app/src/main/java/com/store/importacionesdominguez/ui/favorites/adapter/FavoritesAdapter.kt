package com.store.importacionesdominguez.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.data.model.FavoritesModel
import com.store.importacionesdominguez.databinding.ItemsFavoriteBinding

class FavoritesAdapter (
    private var favoriteItems: List<FavoritesModel>,
    private val onDeleteClickListener: (FavoritesModel) -> Unit,
    private val onClickListener: (String) -> Unit
):RecyclerView.Adapter<FavoritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemsFavoriteBinding.inflate(layoutInflater, parent, false)
        return FavoritesViewHolder(binding)
    }

    fun updateFavoriteItems(newFavoriteItems: MutableList<FavoritesModel>){
        favoriteItems = newFavoriteItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = favoriteItems.size
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favorites: FavoritesModel = favoriteItems[position]
        holder.bind(favorites, onDeleteClickListener, onClickListener)
    }
}