package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.TipoDocumentoIdentidadModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TipoDocumentoIdentidadService {

    @GET("tipo-documento-identidad")
    suspend fun getTipoDocumentos(): List<TipoDocumentoIdentidadModel>

    @GET("tipo-documento-identidad/activos")
    suspend fun getTipoDocumentoActivos(): List<TipoDocumentoIdentidadModel>

    @GET("tipo-documento-identidad/inactivos")
    suspend fun getTipoDocumentoInactivos(): List<TipoDocumentoIdentidadModel>

    @GET("tipo-documento-identidad/{id}")
    suspend fun getTipoDocumento(@Path("id") id: String): TipoDocumentoIdentidadModel

    @POST("tipo-documento-identidad")
    suspend fun createTipoDocumento(@Body tipoDocumento: TipoDocumentoIdentidadModel): TipoDocumentoIdentidadModel

    @PUT("tipo-documento-identidad/{id}")
    suspend fun updateTipoDocumento(@Body tipoDocumento: TipoDocumentoIdentidadModel, @Path("id") id: String): TipoDocumentoIdentidadModel

    @DELETE("tipo-documento-identidad/{id}")
    suspend fun deleteTipoDocumento(@Path("id") id: String): TipoDocumentoIdentidadModel
}