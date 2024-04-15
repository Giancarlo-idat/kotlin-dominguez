package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.model.EmpleadoModel
import retrofit2.Response
import retrofit2.http.GET

interface ProfileService {

    @GET("profile/info/client")
    suspend fun getProfileClient(): Response<ClienteModel>
}