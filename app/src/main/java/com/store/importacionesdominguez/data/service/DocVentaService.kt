package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.DocVentaModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DocVentaService {


    @POST("document/docventa")
    suspend fun createDocVenta(@Body docVenta: DocVentaModel): Response<DocVentaModel>

    @GET("document/docventa")
    suspend fun getDocVentas(): Response<List<DocVentaModel>>

}