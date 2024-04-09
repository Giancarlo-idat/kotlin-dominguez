package com.store.importacionesdominguez.data.model

import java.math.BigDecimal

data class FavoritesModel (
    val id: String,
    val modelo: String,
    val precio: BigDecimal,
    var cantidad: Int,
    val products: ProductModel,
    val marca: String,
    val imagen: String
)