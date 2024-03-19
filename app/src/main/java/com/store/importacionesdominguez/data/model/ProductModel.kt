package com.store.importacionesdominguez.data.model

import java.io.Serializable

data class ProductModel(
    val id: String,
    val model: String?,
    val price: String?,
    val image: String,
    val description: String?,
    val category: String?,
    val marca: String?
)
