package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.TipoTransaccionModel
import retrofit2.Response
import retrofit2.http.GET

interface TipoTransaccionService {

    @GET("/tipo-transaccion")
    suspend fun getTipoTransaccion(): Response<List<TipoTransaccionModel>>
}