    package com.store.importacionesdominguez.api

    import com.store.importacionesdominguez.data.model.ClientStripeModel
    import com.store.importacionesdominguez.data.model.ClienteModel
    import com.store.importacionesdominguez.data.model.PaymentIntentDTO
    import com.store.importacionesdominguez.utils.Utils.SECRET_KEY
    import retrofit2.Response
    import retrofit2.http.Headers
    import retrofit2.http.POST
    import retrofit2.http.Query
    import java.math.BigDecimal

    interface ApiInterface {

        @Headers("Authorization: Bearer $SECRET_KEY", "Stripe-Version: 2022-11-15")
        @POST("v1/ephemeral_keys")
        suspend fun getEphemeralKey(@Query("customer") customer: String): Response<ClientStripeModel>

        @Headers("Authorization: Bearer $SECRET_KEY")
        @POST("v1/payment_intents")
        suspend fun getPaymentIntent(
            @Query("customer") customer: String,
            @Query("amount") amount: BigDecimal,
            @Query("currency") currency: String = "PE",
            @Query("automatic_payment_methods[enabled]") automatePay: Boolean = true,
            @Query("payment_method_types[]") paymentMethodTypes: String = "card"
        ): Response<PaymentIntentDTO>


    }