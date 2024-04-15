package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.PaymentIntentModel
import com.store.importacionesdominguez.data.model.PaymentResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.math.BigDecimal

interface PaymentService {

    @POST("stripe/payment/create")
    fun createPaymentLink(@Body paymentIntentModel: PaymentIntentModel): Call<PaymentResponse>


}