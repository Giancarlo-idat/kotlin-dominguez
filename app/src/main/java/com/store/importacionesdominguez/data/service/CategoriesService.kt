package com.store.importacionesdominguez.data.service

import com.store.importacionesdominguez.data.model.CategoriaModel
import retrofit2.Response
import retrofit2.http.*

interface CategoriesService {

    @GET("categorias")
    suspend fun getCategories(): Response<List<CategoriaModel>>

    @GET("categorias/activos")
    suspend fun getActiveCategories(): Response<List<CategoriaModel>>

    @GET("categorias/inactivos")
    suspend fun getInactiveCategories(): Response<List<CategoriaModel>>

    @GET("categorias/{id}")
    suspend fun getCategory(@Path("id") id: String): Response<CategoriaModel>

    @POST("categorias")
    suspend fun createCategory(@Body category: CategoriaModel): Response<CategoriaModel>

    @PUT("categorias/{id}")
    suspend fun updateCategory(
        @Body category: CategoriaModel,
        @Path("id") id: String
    ): Response<CategoriaModel>

    @DELETE("categorias/{id}")
    suspend fun deleteCategory(@Path("id") id: String): Response<CategoriaModel>

}