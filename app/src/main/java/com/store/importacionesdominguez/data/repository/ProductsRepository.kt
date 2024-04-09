package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.data.service.ProductsService
import javax.inject.Inject
import com.store.importacionesdominguez.data.model.Result

class ProductsRepository @Inject constructor(private val productsService: ProductsService) {

    suspend fun getProducts() = productsService.getProducts()

    suspend fun getProductById(id: String) = productsService.getProduct(id)

    suspend fun createProduct(product: ProductModel): Result<ProductModel> {
        val response = productsService.createProduct(product)
        println("Product: $product")
        println(response)
        return if (response.isSuccessful) {
            val productBody = response.body() ?: throw Exception("Error al crear producto")
            println("Producto creado: $productBody")
            Result.Success(productBody)
        } else {
            println("Error al crear producto: ${response.message()}")
            Result.Error("Error al crear producto: ${response.message()}")
        }
    }

    suspend fun getProductByModel(model: String) = productsService.getProductsByModel(model)

    suspend fun getProductByBrand(brand: String) = productsService.getProductsByBrand(brand)

    suspend fun getProductByCategory(category: String) = productsService.getProductsByCategory(category)

    suspend fun updateProduct(product: ProductModel, id: String) =
        productsService.updateProduct(product, id)

    suspend fun deleteProduct(id: String) = productsService.deleteProduct(id)
}