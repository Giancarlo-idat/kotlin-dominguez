package com.store.importacionesdominguez.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class DocVentaModel(
    @SerializedName("cliente")
    val cliente: ClienteModel,
    @SerializedName("tipoTransaccion")
    val tipoTransaccion: TipoTransaccionModel,
    @SerializedName("direccionEnvio")
    val direccionEnvio: String,
    @SerializedName("fechaEntrega")
    val fechaEntrega: LocalDate,
    @SerializedName("detalles")
    val detalles: List<DocDetalleVentaModal>
)
