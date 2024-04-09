package com.store.importacionesdominguez.ui.categories.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.databinding.FragmentCategoriesBinding
import com.store.importacionesdominguez.ui.categories.adapter.CategoriesAdapter
import com.store.importacionesdominguez.ui.categories.viewmodel.CategoriesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null
    private val _binding get() = binding!!

    private val viewModelCategories: CategoriesViewModel by viewModels()
    private val categoriesAdapter = CategoriesAdapter { categoryId ->
        // Navegar a la pantalla de productos
        navigateCategoryDetail(categoryId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return _binding.root
    }


    private fun navigateCategoryDetail(categoryId: String) {
        val category = fetchCategoryById(categoryId)
        category.let {
            // Navegar a la pantalla de detalle de categoría
        }
    }


    private fun getCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModelCategories.categories.collect() { categoriesResponse ->
                if (categoriesResponse.isSuccessful) {
                    val categoriesList = categoriesResponse.body()
                    categoriesList?.let {
                        categoriesAdapter.setData(it)
                    }
                } else {
                    categoriesResponse.errorBody()?.let {
                        // Manejar error
                        val errorMessage = categoriesResponse.errorBody().toString()
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewModelCategories.getCategories()
    }

    private fun fetchCategoryById(productId: String) {
        viewModelCategories.getCategoryById(productId)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}