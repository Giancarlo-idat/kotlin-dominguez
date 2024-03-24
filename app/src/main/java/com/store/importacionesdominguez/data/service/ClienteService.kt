package com.store.importacionesdominguez.data.service


import com.store.importacionesdominguez.data.model.ClienteModel
import retrofit2.Response
import retrofit2.http.*


interface ClienteService {

    @GET("clientes")
    suspend fun getClientes(): List<ClienteModel>

    @GET("clientes/activos")
    suspend fun getClientesActivos(): List<ClienteModel>

    @GET("clientes/inactivos")
    suspend fun getClientesInactivos(): List<ClienteModel>

    @GET("clientes/{id}")
    suspend fun getCliente(@Path("id") id: String): ClienteModel

    @POST("clientes")
    suspend fun createCliente(@Body cliente: ClienteModel): Response<ClienteModel>

    @PUT("clientes/{id}")
    suspend fun updateCliente(@Body cliente: ClienteModel, @Path("id") id: String): ClienteModel

    @DELETE("clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: String): ClienteModel
}
