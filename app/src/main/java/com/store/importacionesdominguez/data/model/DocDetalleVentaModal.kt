package com.store.importacionesdominguez.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class DocDetalleVentaModal(
    @SerializedName("productos")
    val productos: ProductModel,
    @SerializedName("cantidad")
    val cantidad: Int,
    @SerializedName("precioUnitario")
    val precioUnitario: BigDecimal,
    @SerializedName("precioTotal")
    val precioTotal: BigDecimal
)
