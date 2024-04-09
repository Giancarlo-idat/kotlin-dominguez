package com.store.importacionesdominguez.ui.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.model.CategoriaModel
import com.store.importacionesdominguez.data.repository.CategoriesRepository
import com.store.importacionesdominguez.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val map = HashMap<String, String>()

    private val _categories =
        MutableStateFlow<Response<List<CategoriaModel>>>(Response.success(emptyList()))
    val categories: StateFlow<Response<List<CategoriaModel>>> = _categories

    private val _category =
        MutableStateFlow<Response<CategoriaModel>>(
            Response.success(
                CategoriaModel(
                    id = "",
                    nombre = "",
                    descripcion = "",
                    imagen = ""
                )
            )
        )

    val category: StateFlow<Response<CategoriaModel>> = _category

    fun getCategories() {
        viewModelScope.launch {
            try {
                val categoriesListResponse = categoriesRepository.getCategories()
                println("CategoriesList:  $categoriesListResponse")
                _categories.value = categoriesListResponse
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

    fun getCategoryById(categoryId: String) {
        viewModelScope.launch {
            try {
                val categorieResponse = categoriesRepository.getCategoryById(categoryId)
                println("Categorie:  $categorieResponse")
                _category.value = categorieResponse
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

}
