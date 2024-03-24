package com.store.importacionesdominguez.ui.product.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.store.importacionesdominguez.databinding.FragmentProductSpecificationsBinding

class ProductSpecificationsFragment : Fragment() {

    private var _binding: FragmentProductSpecificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductSpecificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val specification = arguments?.getString(ARG_SPECIFICATION)
        binding.txtSpecificationProduct.text = specification
    }

    companion object {
        const val ARG_SPECIFICATION = "arg_specification"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}