package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.DocVentaModel
import com.store.importacionesdominguez.data.model.PaymentIntentModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CheckoutService {

    @POST("/stripe/payment/create")
    suspend fun createPaymentIntent(@Body checkout: PaymentIntentModel): Response<PaymentIntentModel>

    @GET("/profile/myorders/client")
    suspend fun getMyOrders(): Response<List<DocVentaModel>>

    @GET("/profile/myorders/details")
    suspend fun getOrdersDetails(): Response<List<DocVentaModel>>


    @GET("/profile/order/{orderId}/myorders/details")
    suspend fun getMyOrderDetails(@Path("orderId") orderId: String): Response<DocVentaModel>
}