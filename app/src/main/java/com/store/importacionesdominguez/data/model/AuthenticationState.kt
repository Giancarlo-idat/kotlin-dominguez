package com.store.importacionesdominguez.data.model

sealed class AuthenticationState {
    data object Authenticated : AuthenticationState()
    data object Unauthenticated : AuthenticationState()
    data object Loading : AuthenticationState()
    data class Error(val message: String) : AuthenticationState()
}