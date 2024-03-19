package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.RolModel
import retrofit2.http.*

interface RolService {

    @GET("rol")
    suspend fun getRoles(): List<RolModel>

    @GET("rol/activos")
    suspend fun getRolActivos(): List<RolModel>

    @GET("rol/inactivos")
    suspend fun getRolesInactivos(): List<RolModel>

    @GET("rol/rolId")
    suspend fun getRol(@Query("id") id: String): RolModel

    @POST("rol")
    suspend fun createRol(@Body rol: RolModel): RolModel

    @PUT("rol/{id}")
    suspend fun updateRol(@Body rol: RolModel, @Path("id") id: String): RolModel

    @DELETE("rol/{id}")
    suspend fun deleteRol(@Path("id") id: String): RolModel
}