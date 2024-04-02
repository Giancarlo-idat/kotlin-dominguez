package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.ProductModel
import retrofit2.Response
import retrofit2.http.*

interface ProductsService {

    @GET("productos")
    suspend fun getProducts(): Response<List<ProductModel>>

    @GET("productos/activos")
    suspend fun getActiveProducts(): Response<List<ProductModel>>

    @GET("productos/inactivos")
    suspend fun getInactiveProducts(): Response<List<ProductModel>>

    @GET("productos/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<ProductModel>

    @POST("productos")
    suspend fun createProduct(@Body product: ProductModel): Response<ProductModel>

    @PUT("productos/{id}")
    suspend fun updateProduct(
        @Body product: ProductModel,
        @Path("id") id: String
    ): Response<ProductModel>

    @DELETE("productos/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<ProductModel>


}