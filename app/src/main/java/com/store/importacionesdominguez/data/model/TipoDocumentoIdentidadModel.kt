package com.store.importacionesdominguez.data.model

data class TipoDocumentoIdentidadModel(

    val id: String,
    val nombre: String,
    val estado: Boolean = true,
    val empleados: Set<EmpleadoModel> = HashSet(),
)