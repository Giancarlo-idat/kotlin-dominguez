package com.store.importacionesdominguez.ui.checkout.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.store.importacionesdominguez.data.model.Result
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.repository.DocVentaRepository
import com.store.importacionesdominguez.data.repository.ProfileRepository
import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.model.DocDetalleVentaModal
import com.store.importacionesdominguez.data.model.DocVentaModel
import com.store.importacionesdominguez.data.model.TipoTransaccionModel
import com.store.importacionesdominguez.utils.preferences.CartManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DocVentaViewModel @Inject constructor(
    private val docVentaRepository: DocVentaRepository,
    private val clientRepository: ProfileRepository,
    @SuppressLint("StaticFieldLeak") private val context: Context
) : ViewModel() {

    private val _cliente = MutableStateFlow<ClienteModel?>(null)
    val cliente: Flow<ClienteModel?> get() = _cliente.asStateFlow()

    init {
        getClienteProfile()
    }

    fun crearDocVenta(fechaEntrega: LocalDate) {
        val cliente = _cliente.value ?: return
        val detallesVenta = getDetallesVentaFromCart()
        val tipoTransaccionSelected = TipoTransaccionModel(
            id = "TTR-VENTA-TOC201",
            nombre = "Boleta"
        )
        val direccionEnvio = cliente.direccion

        // Creamos el objeto DocVentaModel con los datos obtenidos
        val docVenta = DocVentaModel(
            cliente = cliente,
            tipoTransaccion = tipoTransaccionSelected,
            direccionEnvio = direccionEnvio,
            fechaEntrega = fechaEntrega,
            detalles = detallesVenta
        )

        // Llamamos al método del repositorio para crear el documento de venta
        viewModelScope.launch {
            try {
                val result = docVentaRepository.createDocVenta(docVenta)
                Log.d("DocVentaViewModel", "Resultado de la creación del documento de venta: $result")
                when (result) {
                    is Result.Success -> {
                        // Manejar el caso de éxito
                        Log.d("DocVentaViewModel", "Documento de venta creado con éxito")
                        Toast.makeText(
                            context,
                            "Documento de venta creado con éxito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Error -> {
                        // Manejar el caso de error
                        Log.e("DocVentaViewModel", "Error al crear el documento de venta")
                        Toast.makeText(
                            context,
                            "Error al crear el documento de venta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        // Manejar otros casos
                        Log.e("DocVentaViewModel", "Error al crear el documento de venta")
                        Toast.makeText(
                            context,
                            "Error al crear el documento de venta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                // Manejar errores de red u otros errores
                Log.e("DocVentaViewModel", "Error al crear el documento de venta", e)
                Log.e("DocVentaViewModel ", "Error al crear el documento de venta: ${e.message} ")
            }
        }
    }

    private fun getDetallesVentaFromCart(): List<DocDetalleVentaModal> {
        val cart = CartManager.getCart(context)


        return cart.map { cartItem ->
            DocDetalleVentaModal(
                productos = cartItem.products,
                cantidad = cartItem.cantidad,
                precioUnitario = cartItem.precio,
                precioTotal = cartItem.precio * BigDecimal(cartItem.cantidad)
            )
        }
    }

    fun getClienteProfile() {
        viewModelScope.launch {
            try {
                val response = clientRepository.getProfileCliente()
                Log.d("DocVentaViewModel", "Respuesta de obtener datos del cliente: $response")
                if (response.isSuccessful) {
                    _cliente.value = response.body()
                } else {
                    // Manejar el caso de respuesta fallida
                    Log.e("DocVentaViewModel", "Error al obtener datos del cliente")
                    Toast.makeText(
                        context,
                        "Error al obtener datos del cliente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Manejo de errores de red u otros errores
                Log.e("DocVentaViewModel", "Error al obtener datos del cliente", e)
            }
        }
    }


}
