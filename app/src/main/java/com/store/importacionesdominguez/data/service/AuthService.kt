package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.LoginResponse
import com.store.importacionesdominguez.data.model.SignInModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun iniciarSesion(@Body signIn: SignInModel): Response<LoginResponse>
}