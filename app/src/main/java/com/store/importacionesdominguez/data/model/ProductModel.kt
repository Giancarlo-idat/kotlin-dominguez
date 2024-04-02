package com.store.importacionesdominguez.data.model

import com.fasterxml.jackson.databind.ObjectMapper
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
) {
    fun toJson(): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(this)
    }

    companion object {
        fun fromJson(json: String): ProductModel {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(json, ProductModel::class.java)
        }
    }
}
