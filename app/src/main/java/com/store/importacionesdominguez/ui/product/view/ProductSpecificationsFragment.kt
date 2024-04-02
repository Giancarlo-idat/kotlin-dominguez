package com.store.importacionesdominguez.ui.product.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.type.TypeReference
import com.store.importacionesdominguez.databinding.FragmentProductSpecificationsBinding

class ProductSpecificationsFragment : Fragment() {

    private var _binding: FragmentProductSpecificationsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la especificación técnica del Bundle
        val jsonFichaTecnica = arguments?.getString("technicalSheet")

        val fichaTecnica: Map<String, Any> = ObjectMapper().readValue(
            jsonFichaTecnica,
            object : TypeReference<Map<String, Any>>() {})

        // Verificar si hay especificación técnica disponible
        if (fichaTecnica.isNotEmpty()) {
            // Mostrar el contenedor de la especificación técnica
            binding.txtSpecificationProduct.text = fichaTecnica.toString()
            binding.fragmentProductSpecifications.visibility = View.VISIBLE
        } else {
            // Ocultar el contenedor de la especificación técnica
            binding.fragmentProductSpecifications.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
