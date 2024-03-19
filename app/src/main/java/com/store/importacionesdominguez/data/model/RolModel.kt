package com.store.importacionesdominguez.data.model

data class RolModel(

    val id: String,
    val nombre: String,
    val estado: Boolean = true,
    val descripcion: String? = null,
    val empleados: Set<EmpleadoModel> = HashSet(),
    val clientes: Set<ClienteModel> = HashSet(),
    val permisos: Set<RolPermisoModel> = HashSet()
)