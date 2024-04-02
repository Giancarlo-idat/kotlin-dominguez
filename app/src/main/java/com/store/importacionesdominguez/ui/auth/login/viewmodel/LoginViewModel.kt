package com.store.importacionesdominguez.ui.auth.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.AuthenticationState
import com.store.importacionesdominguez.data.model.Result
import com.store.importacionesdominguez.data.model.SignInModel
import com.store.importacionesdominguez.data.repository.LoginRepository
import com.store.importacionesdominguez.utils.preferences.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {

    // LiveData para almacenar el estado de autenticación
    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState> = _authenticationState

    // Propiedad booleana para indicar si el usuario está autenticado
    private val _isAuthenticated = MutableLiveData<Boolean>()
    // val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    fun iniciarSesion(singIn: SignInModel, context: Context) {
        _authenticationState.value = AuthenticationState.Loading
        viewModelScope.launch {
            val result = repository.iniciarSesion(singIn)
            when (result) {
                is Result.Success -> {
                    val token = result.data.token
                    val email = result.data.email
                    _authenticationState.value = AuthenticationState.Authenticated
                    if (token != null) {
                        // Guardar token en SharedPreferences solo si no es nulo
                        SharedPreferences.saveToken(context, token)
                        SharedPreferences.saveTokenAndEmail(context, token, email)
                        _isAuthenticated.value = true
                    } else {
                        _authenticationState.value = AuthenticationState.Error("Error de autenticación")
                    }
                }

                is Result.Loading -> {
                    _authenticationState.value = AuthenticationState.Loading
                }

                is Result.Error -> {
                    println("Result  $result")

                    val error = result.message

                    println("Error: $error")
                    _authenticationState.value = AuthenticationState.Error("Error de autenticación")
                    // Mostrar mensaje de error
                    Result.Error(error)
                    // Actualizar estado de autenticación a false
                    _isAuthenticated.value = false
                }
            }
        }
    }

    fun cerrarSesion(context: Context, navController: NavController) {
        _authenticationState.value = AuthenticationState.Unauthenticated
        SharedPreferences.clearAuthenticationData(context)
        _isAuthenticated.value = false
        navController.navigate(R.id.homeFragment)
    }
}