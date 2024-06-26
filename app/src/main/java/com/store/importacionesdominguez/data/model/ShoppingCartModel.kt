package com.store.importacionesdominguez.data.model

import java.math.BigDecimal

data class ShoppingCartModel (

    val id: String,
    val modelo: String,
    val marca : String?,
    val precio: BigDecimal,
    val imagen: String,
    var cantidad: Int,
    val products: ProductModel,
)