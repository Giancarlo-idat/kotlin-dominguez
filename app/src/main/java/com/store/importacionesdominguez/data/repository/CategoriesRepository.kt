package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.CategoriaModel
import com.store.importacionesdominguez.data.model.Result
import com.store.importacionesdominguez.data.service.CategoriesService
import javax.inject.Inject

class CategoriesRepository @Inject constructor(private val categoriesService: CategoriesService) {


    suspend fun getCategories() = categoriesService.getCategories()

    suspend fun getCategoryById(id: String) = categoriesService.getCategory(id)

    suspend fun createCategory(category: CategoriaModel): Result<CategoriaModel> {
        val response = categoriesService.createCategory(category)
        println("Category: $category")
        println(response)
        return if (response.isSuccessful) {
            val categoryBody = response.body() ?: throw Exception("Error al crear la categoria")
            println("Category creado: $categoryBody")
            Result.Success(categoryBody)
        } else {
            println("Error al crear category: ${response.message()}")
            Result.Error("Error al crear category: ${response.message()}")
        }
    }

    suspend fun updateCategory(category: CategoriaModel, id: String) =
        categoriesService.updateCategory(category, id)

    suspend fun deleteCategory(id: String) = categoriesService.deleteCategory(id)

}