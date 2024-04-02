package com.store.importacionesdominguez.ui.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.model.CategoriaModel
import com.store.importacionesdominguez.data.model.Result
import com.store.importacionesdominguez.data.model.ProductModel
import com.store.importacionesdominguez.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    val map = HashMap<String, String>()

    private val _productos =
        MutableStateFlow<Response<List<ProductModel>>>(Response.success(emptyList()))
    val productos: StateFlow<Response<List<ProductModel>>> = _productos

    private val _product =
        MutableStateFlow<Response<ProductModel>>(
            Response.success(
                ProductModel(
                    categoria = CategoriaModel(id = "", nombre = "", descripcion = "", imagen = ""),
                    descripcion = "",
                    fichaTecnica = map,
                    imagen = "",
                    marca = "",
                    modelo = "",
                    precio = BigDecimal.ZERO,
                    stock = 0
                )
            )
        )
    val product: StateFlow<Response<ProductModel>> = _product

    fun getProducts() {
        viewModelScope.launch {
            try {
                val productsListResponse = productsRepository.getProducts()
                println("ProductList:  $productsListResponse")
                _productos.value = productsListResponse
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            try {
                val productResponse = productsRepository.getProductById(productId)
                _product.value = productResponse
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

}