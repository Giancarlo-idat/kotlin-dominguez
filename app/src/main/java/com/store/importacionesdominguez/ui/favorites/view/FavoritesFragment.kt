package com.store.importacionesdominguez.ui.favorites.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.FavoritesModel
import com.store.importacionesdominguez.databinding.FragmentFavoritesBinding
import com.store.importacionesdominguez.ui.favorites.adapter.FavoritesAdapter
import com.store.importacionesdominguez.ui.product.viewmodel.ProductsViewModel
import com.store.importacionesdominguez.utils.preferences.FavoritesManager


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    private lateinit var favoritesAdapter: FavoritesAdapter

    private val viewModelProducts: ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter()
        onBackPressed()
        cartToPressed()
    }

    private fun adapter() {
        val favoritesItems = FavoritesManager.getFavorites(requireContext())
        favoritesAdapter = FavoritesAdapter(
            favoritesItems,
            onDeleteClickListener = { item -> onDeleteItemClicked(item) },
            onClickListener = { itemId -> onItemClicked(itemId) }
        )

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }

    }


    private fun onDeleteItemClicked(item: FavoritesModel) {
        FavoritesManager.deleteFavorite(requireContext(), item)
        refreshFavorites()
    }

    private fun onItemClicked(itemId: String) {
        // Lógica para abrir el detalle del producto
        viewModelProducts.getProductById(itemId)
    }


    private fun onBackPressed() {
        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun cartToPressed() {
        binding.btnImgBag.setOnClickListener {
            // Navegar hacia el carrito
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToCartFragment()
            findNavController().navigate(action)
        }
    }

    private fun refreshFavorites() {
        val favoritesItems = FavoritesManager.getFavorites(requireContext())
        favoritesAdapter.updateFavoriteItems(favoritesItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}