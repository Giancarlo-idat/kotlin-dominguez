package com.store.importacionesdominguez.ui.checkout.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.api.ApiUtilities
import com.store.importacionesdominguez.data.model.FechaEnvio
import com.store.importacionesdominguez.databinding.FragmentCheckoutBinding
import com.store.importacionesdominguez.ui.checkout.adapter.FechaEnviosAdapter
import com.store.importacionesdominguez.ui.checkout.viewModel.DocVentaViewModel
import com.store.importacionesdominguez.utils.preferences.CartManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.store.importacionesdominguez.utils.Utils.PUBLISHABLE_KEY
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    // View Model
    private val viewDocVentaModel: DocVentaViewModel by viewModels()

    private lateinit var paymentSheet: PaymentSheet
    private lateinit var clientId: String
    private lateinit var ephemeralKey: String
    private lateinit var clientSecret: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        PaymentConfiguration.init(requireContext(), PUBLISHABLE_KEY)

        observeClienteAddress()
        getClienteProfile()
        getClienteProfile()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPaymentSheet()
        setupRecyclerView()
        calcularPrecioTotal()
    }

    private var apiInterface = ApiUtilities.getApiInterface()

    private fun getEphemeralKey(id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = apiInterface.getEphemeralKey(id)
            withContext(Dispatchers.Main) {
                if (res.isSuccessful && res.body() != null) {
                    ephemeralKey = res.body()!!.id.toString()
                    getPaymentIntent(id, ephemeralKey)
                }
            }
        }
    }

    private fun getPaymentIntent(id: String, ephemeralKey: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val calcularPrecio = calcularPrecioTotal()
            val res = apiInterface.getPaymentIntent(id, calcularPrecio, "PE")
            withContext(Dispatchers.Main) {
                if (res.isSuccessful && res.body() != null) {
                    clientSecret = res.body()!!.id
                    Toast.makeText(
                        requireContext(),
                        "Pago Procesado: $clientSecret",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupPaymentSheet() {
        paymentSheet = PaymentSheet(this@CheckoutFragment, ::onPaymentSheetResult)
    }

    private fun paymentFlow() {
        paymentSheet.presentWithPaymentIntent(
            clientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Importaciones Dominguez",
                PaymentSheet.CustomerConfiguration(
                    id = clientId,
                    ephemeralKeySecret = ephemeralKey
                )
            )
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setupRecyclerView() {
        val proximasFechas = obtenerProximasFechas()

        val fechaEnviosAdapter = FechaEnviosAdapter(proximasFechas) { fechaSeleccionada ->
            binding.tvFechaEntrega.text = "Fecha Entrega ${
                fechaSeleccionada.format(
                    DateTimeFormatter.ofPattern(
                        "dd 'de' MMMM",
                        Locale("es", "ES")
                    )
                )
            }"
            binding.btnPayment.setOnClickListener {
                if (::clientSecret.isInitialized) {
                    paymentFlow()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: No se pudo iniciar el pago",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        binding.rvFechas.apply {
            adapter = fechaEnviosAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }


    private fun observeClienteAddress() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewDocVentaModel.cliente.collect { cliente ->
                cliente?.let {
                    val direccion = it.direccion
                    showAddress(direccion)
                }
            }
        }

    }

    private fun getClienteProfile() {
        viewDocVentaModel.getClienteProfile()
    }

    private fun showAddress(address: String) {
        binding.tvAddress.text = address
    }

    @SuppressLint("SetTextI18n")
    private fun calcularPrecioTotal(): BigDecimal {
        val cartItems = CartManager.getCart(requireContext())
        val totalPrice: BigDecimal?
        val subTotal = cartItems.sumOf { it.precio.multiply(BigDecimal(it.cantidad)) }
        val costoEnvio = costoEnvio()

        totalPrice = subTotal + (costoEnvio)

        binding.tvSubTotalValueCheckout.text = String.format("S/. %.2f", subTotal)
        binding.tvTotalValueCheckout.text = String.format("S/. %.2f", totalPrice)

        return totalPrice
    }

    @SuppressLint("SetTextI18n")
    private fun costoEnvio(): BigDecimal {
        val costoEnvio = BigDecimal("5.00")
        binding.tvTotalEnvioCheckout.text = "S/. $costoEnvio"
        return costoEnvio
    }


    private fun obtenerProximasFechas(): List<FechaEnvio> {
        val proximasFechas = mutableListOf<FechaEnvio>()
        val calendar = Calendar.getInstance()

        for (i in 0 until 7) {
            val fecha = LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            val costoEnvio = BigDecimal("5.00")
            proximasFechas.add(FechaEnvio(fecha, costoEnvio))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return proximasFechas
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        if (paymentSheetResult is PaymentSheetResult.Completed) {
            Toast.makeText(requireContext(), "Pago completado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Pago cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
