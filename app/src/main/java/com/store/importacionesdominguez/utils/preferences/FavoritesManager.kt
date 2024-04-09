package com.store.importacionesdominguez.utils.preferences

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.store.importacionesdominguez.data.model.FavoritesModel

object FavoritesManager {

    private const val FAVORITES_KEY = "FAVORITES"
    private const val MY_FAVORITES = "MY_FAVORITES"


    fun getFavorites(context: Context): MutableList<FavoritesModel> {
        val favoritesManager = context.getSharedPreferences(MY_FAVORITES, Context.MODE_PRIVATE)
        val favoritesJson = favoritesManager.getString(FAVORITES_KEY, "[]")
        return Gson().fromJson(
            favoritesJson,
            object : TypeToken<MutableList<FavoritesModel>>() {}.type
        )
            ?: mutableListOf()
    }

    fun saveFavorites(context: Context, favorites: List<FavoritesModel>) {
        val favoritesJson = Gson().toJson(favorites)
        context.getSharedPreferences(MY_FAVORITES, Context.MODE_PRIVATE)
            .edit()
            .putString(FAVORITES_KEY, favoritesJson)
            .apply()
    }

    fun clearFavorites(context: Context) {
        context.getSharedPreferences(MY_FAVORITES, Context.MODE_PRIVATE)
            .edit()
            .remove(FAVORITES_KEY)
            .apply()
    }

    fun addFavorite(context: Context, item: FavoritesModel) {
        val favoritesItems = getFavorites(context).toMutableList()
        val existingItem = favoritesItems.find { it.id == item.id }
        if (existingItem == null) {
            // Agregar el producto a favoritos
            favoritesItems.add(item)
        }
        saveFavorites(context, favoritesItems)
    }

    fun deleteFavorite(context: Context, item: FavoritesModel) {
        val favoritesItems = getFavorites(context).toMutableList()
        val existingItem = favoritesItems.find { it.id == item.id }
        if (existingItem != null) {
            // Eliminar el producto de favoritos
            favoritesItems.remove(item)
        }
        saveFavorites(context, favoritesItems)
    }
}