package com.store.importacionesdominguez.data.model


// Sealed class Result para manejar el resultado de las llamadas API.
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    data class Loading(val nothing: Nothing? = null) : Result<Nothing>()
}
