package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.RolModel
import com.store.importacionesdominguez.data.service.RolService
import javax.inject.Inject

class RolRepository @Inject constructor( private val rolService: RolService){

    suspend fun getRoles() = rolService.getRoles()

    suspend fun getRolActivos() = rolService.getRolActivos()

    suspend fun getRolesInactivos() = rolService.getRolesInactivos()

    suspend fun getRol(id: String) = rolService.getRol(id)

    suspend fun createRol(rol: RolModel) = rolService.createRol(rol)

    suspend fun updateRol(rol: RolModel, id: String) = rolService.updateRol(rol, id)

    suspend fun deleteRol(id: String) = rolService.deleteRol(id)
}