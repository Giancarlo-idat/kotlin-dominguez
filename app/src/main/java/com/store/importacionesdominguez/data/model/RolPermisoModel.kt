package com.store.importacionesdominguez.data.model

data class RolPermisoModel(
    val id: String,
    val nombre: String,
    val rol: RolModel,
    val estado: Boolean = true,

    )
