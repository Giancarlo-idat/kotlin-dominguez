package com.store.importacionesdominguez.data.model

import java.io.Serializable
import java.math.BigDecimal

data class ProductModel(
    val id : String ? = null,
    val modelo: String,
    val descripcion: String,
    val precio: BigDecimal,
    val marca: String,
    val stock: Int,
    val imagen: String,
    val categoria: CategoriaModel,
    val fichaTecnica: Map<String, Any>
)
