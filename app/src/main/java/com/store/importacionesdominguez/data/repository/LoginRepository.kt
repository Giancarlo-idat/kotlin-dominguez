package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.LoginResponse
import com.store.importacionesdominguez.data.model.SignInModel
import com.store.importacionesdominguez.data.service.AuthService
import com.store.importacionesdominguez.data.model.Result
import javax.inject.Inject

class LoginRepository @Inject constructor(private val authService: AuthService) {

    suspend fun iniciarSesion(signInModel: SignInModel): Result<LoginResponse> {
        val credentials = SignInModel(signInModel.email, signInModel.password)
        println("Credentials Repository: $credentials")
        val response = authService.iniciarSesion(credentials)
        println("RESPONSE $response")
        println("Response body: ${response.body()}")
        return if (response.isSuccessful) {
            val loginResponse = response.body() ?: throw Exception("Error al iniciar sesión")
            println("Login Response: $loginResponse")
            Result.Success(loginResponse)
        } else {
            println("Error al iniciar sesión: ${response.message()}")
            Result.Error("Error al iniciar sesión: ${response.message()}")
        }
    }

}