package com.store.importacionesdominguez.utils.preferences

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.store.importacionesdominguez.data.model.ShoppingCartModel

object CartManager {

    private const val CART_KEY = "CART"
    private const val MY_CART = "MY_CART"

    /* Recupera los datos del carrito compras */
    fun getCart(context: Context): MutableList<ShoppingCartModel> {
        val sharedPreferences = context.getSharedPreferences(MY_CART, Context.MODE_PRIVATE)
        val cartJson = sharedPreferences.getString(CART_KEY, "[]")
        return Gson().fromJson(
            cartJson,
            object : TypeToken<MutableList<ShoppingCartModel>>() {}.type
        )
            ?: mutableListOf()
    }


    /* Guarda los productos en el carrito en SharedPreferences */
    fun saveCart(context: Context, cart: List<ShoppingCartModel>) {
        val cartJson = Gson().toJson(cart)
        context.getSharedPreferences(MY_CART, Context.MODE_PRIVATE)
            .edit()
            .putString(CART_KEY, cartJson)
            .apply()
    }

    /*Limpiar el carrito*/
    fun clearCart(context: Context) {
        context.getSharedPreferences(MY_CART, Context.MODE_PRIVATE)
            .edit()
            .remove(CART_KEY)
            .apply()
    }


    /* Agrega un nuevo producto al carrito de compras */
    fun addProduct(context: Context, item: ShoppingCartModel) {
        val cartItems = getCart(context).toMutableList()
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            if (existingItem.cantidad < 10) {
                // Incrementar la cantidad del producto existente en el carrito
                existingItem.cantidad++
            } else {
                Toast.makeText(context, "No puedes agregar más de 10 productos", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            // Agregar el producto al carrito con una cantidad inicial de 1
            cartItems.add(item)
        }
        saveCart(context, cartItems)
    }

    /* Disminiye un producto en el carrito*/
    fun decrementProduct(context: Context, item: ShoppingCartModel) {
        val cartItems = getCart(context).toMutableList()
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            if (existingItem.cantidad > 1) {
                // Decrementar la cantidad del producto existente en el carrito
                existingItem.cantidad--
            } else {
                // Eliminar el producto del carrito si la cantidad es 1
                cartItems.remove(existingItem)
            }
        } else {
            // Agregar el producto al carrito con una cantidad inicial de 1
            cartItems.add(item)
        }
        saveCart(context, cartItems)
    }


    /* Elimina un producto */
    fun deleteProductCart(context: Context, item: ShoppingCartModel) {
        val cartItems = getCart(context).toMutableList()
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            cartItems.remove(existingItem)
        }
        saveCart(context, cartItems)
    }
}
