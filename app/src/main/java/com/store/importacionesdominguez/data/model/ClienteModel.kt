package com.store.importacionesdominguez.data.model

data class ClienteModel (
    val id: String,
    val nombres: String,
    val apellidos: String,
    val direccion: String,
    val email: String,
    val password: String,
    val tipoDocumento: TipoDocumentoIdentidadModel?,
    val numeroDocumento: String,
    val sexo: TipoSexo,
    val telefono: String,
)