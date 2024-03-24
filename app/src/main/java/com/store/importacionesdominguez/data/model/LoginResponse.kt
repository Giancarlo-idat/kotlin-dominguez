package com.store.importacionesdominguez.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("email") val email: String,
    @SerializedName("roles") val roles: List<Role>,
    @SerializedName("status") val status: Int,
    @SerializedName("token") val token: String
)

data class Role(
    val authority: String
)