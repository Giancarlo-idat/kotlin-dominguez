package com.store.importacionesdominguez.ui.checkout.viewModel

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.model.PaymentIntentModel
import com.store.importacionesdominguez.data.model.PaymentResponse
import com.store.importacionesdominguez.data.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _paymentUrl = MutableStateFlow<String?>(null)
    val paymentUrl: StateFlow<String?> get() = _paymentUrl

    fun createPaymentLink(totalPrice: BigDecimal) {
        val paymentIntentModel = PaymentIntentModel(totalPrice)
        viewModelScope.launch {
            try {
                val response = paymentRepository.createPaymentLink(paymentIntentModel)
                Log.d("PaymentViewModel", "Payment URL: ${response.payment_url}")
                _paymentUrl.value = response.payment_url
            } catch (e: Exception) {
                Log.d("PaymentViewModel", "Error al crear el enlace de pago: ${e.message}")
                _paymentUrl.value = null
            }
        }
    }

}