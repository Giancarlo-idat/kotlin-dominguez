package com.store.importacionesdominguez.ui.product.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.store.importacionesdominguez.databinding.FragmentProductDescriptionBinding


class ProductDescriptionFragment : Fragment() {

    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("description")?.let { description ->
            setDescription(description)
        }
    }


    private fun setDescription(description: String) {
        binding.tvDescription.text = description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
