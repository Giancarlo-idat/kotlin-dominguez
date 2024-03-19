package com.store.importacionesdominguez.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Message: ") val message: String,
    @SerializedName("Email: ") val email: String,
    @SerializedName("Roles: ") val roles: List<Role>,
    @SerializedName("Status: ") val status: Int,
    @SerializedName("token: ") val token: String
)

data class Role(
    val authority: String
)