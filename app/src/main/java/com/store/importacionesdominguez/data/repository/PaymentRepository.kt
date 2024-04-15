package com.store.importacionesdominguez.data.repository

import android.util.Log
import com.store.importacionesdominguez.data.model.PaymentIntentModel
import com.store.importacionesdominguez.data.model.PaymentResponse
import com.store.importacionesdominguez.data.service.PaymentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.math.BigDecimal
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val paymentService: PaymentService
) {

    suspend fun createPaymentLink(paymentIntentModel: PaymentIntentModel): PaymentResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = paymentService.createPaymentLink(paymentIntentModel).execute()
                if (response.isSuccessful) {
                    response.body() ?: throw Exception("Response body is null")
                } else {
                    throw Exception("Error en la solicitud: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.d("PaymentRepository", "Error de red: ${e.message}")
                throw Exception("Error de red: ${e.message}")
            }
        }
    }


}