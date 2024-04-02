package com.store.importacionesdominguez.ui.cart.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.FragmentCartBinding
import com.store.importacionesdominguez.databinding.FragmentHomeBinding
import com.store.importacionesdominguez.ui.cart.adapter.ShoppingCartAdapter
import com.store.importacionesdominguez.utils.preferences.CartManager


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingCartAdapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter()
    }

    private fun adapter() {

        val cartItems = CartManager.getCart(requireContext())
        shoppingCartAdapter = ShoppingCartAdapter(
            cartItems,
            onDeleteClickListener = { item -> onDeleteItemClicked(item) },
            onIncrementClickListener = { item -> onIncrementItemClicked(item) },
            onDecrementClickListener = { item -> onDecrementItemClicked(item) }
        )

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = shoppingCartAdapter
        }
    }

    private fun onDeleteItemClicked(item: ShoppingCartModel) {
        // Lógica para eliminar un elemento del carrito
        CartManager.deleteProductCart(requireContext(), item)
        refreshCart()
    }

    private fun onIncrementItemClicked(item: ShoppingCartModel) {
        // Lógica para incrementar la cantidad de un elemento del carrito
        CartManager.addProduct(requireContext(), item)
        refreshCart()
    }

    private fun onDecrementItemClicked(item: ShoppingCartModel) {
        // Lógica para decrementar la cantidad de un elemento del carrito
        CartManager.decrementProduct(requireContext(), item)
        refreshCart()
    }

    private fun refreshCart() {
        val updatedCartItems = CartManager.getCart(requireContext())
        shoppingCartAdapter.updateCartItems(updatedCartItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}