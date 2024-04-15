package com.store.importacionesdominguez.ui.cart.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.importacionesdominguez.data.model.ShoppingCartModel
import com.store.importacionesdominguez.databinding.FragmentCartBinding
import com.store.importacionesdominguez.ui.cart.adapter.ShoppingCartAdapter
import com.store.importacionesdominguez.utils.preferences.CartManager


class ShoppingCartFragment : Fragment() {

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
        getDetailsOrder()
        navigateFragments()

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
        getDetailsOrder()
    }

    private fun onIncrementItemClicked(item: ShoppingCartModel) {
        // Lógica para incrementar la cantidad de un elemento del carrito
        CartManager.addProduct(requireContext(), item)
        refreshCart()
        getDetailsOrder()
    }

    private fun onDecrementItemClicked(item: ShoppingCartModel) {
        // Lógica para decrementar la cantidad de un elemento del carrito
        CartManager.decrementProduct(requireContext(), item)
        refreshCart()
        getDetailsOrder()

    }

    @SuppressLint("SetTextI18n")
    private fun getDetailsOrder() {
        // Lógica para obtener los detalles de la orden
        val cartItems = CartManager.getCart(requireContext())

        // Calcular  la cantidad total de productos en el carrito
        val totalProducts = cartItems.sumOf { it.cantidad }

        //Calcular el subtotal de la orden
        val subtotal = cartItems.sumOf { it.cantidad * it.precio.toDouble() }

        val shippingCost = 10.0

        // Calcular el total de la orden
        val total = subtotal + shippingCost

        // Actualizar los TextView con los valores calculados
        binding.tvProductsValue.text = "Carrito ($totalProducts productos)"
        /* binding.tvSubTotalValue.text = String.format("S/. %.2f", subtotal)
         binding.tvEnvioValue.text = String.format("S/. %.2f", shippingCost)*/
        binding.tvTotalValue.text = String.format("S/. %.2f", subtotal)
    }

    private fun navigateFragments() {
        navigateToCheckout()
    }

    private fun refreshCart() {
        val updatedCartItems = CartManager.getCart(requireContext())
        shoppingCartAdapter.updateCartItems(updatedCartItems)
    }

    // Navegar hacia el checkout
    private fun navigateToCheckout() {
        Log.d("Navegar hacia el checkout", "Navegar hacia el checkout")
        // Lógica para navegar hacia el checkout
        binding.btnCheckout.setOnClickListener {
            val action = ShoppingCartFragmentDirections.actionCartFragmentToCheckoutFragment()
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}