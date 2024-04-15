package com.store.importacionesdominguez.data.model

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("payment_url") val payment_url: String
)