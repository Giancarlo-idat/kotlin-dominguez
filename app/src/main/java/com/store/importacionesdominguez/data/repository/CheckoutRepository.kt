package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.PaymentIntentModel
import com.store.importacionesdominguez.data.service.CheckoutService
import com.store.importacionesdominguez.data.model.Result
import javax.inject.Inject

class CheckoutRepository @Inject constructor(
    private val checkoutService: CheckoutService
){

    suspend fun createCheckout(checkout: PaymentIntentModel): Result<PaymentIntentModel> {
        val response = checkoutService.createPaymentIntent(checkout)
        println("Checkout: $checkout")
        println(response)
        return if (response.isSuccessful) {
            val checkoutBody = response.body() ?: throw Exception("Error al crear checkout")
            println("Checkout creado: $checkoutBody")
            Result.Success(checkoutBody)
        } else {
            println("Error al crear checkout: ${response.message()}")
            Result.Error("Error al crear checkout: ${response.message()}")
        }
    }

    suspend fun getMyOrders() = checkoutService.getMyOrders()

    suspend fun getOrdersDetails() = checkoutService.getOrdersDetails()

    suspend fun getMyOrderDetails(orderId: String) = checkoutService.getMyOrderDetails(orderId)


}