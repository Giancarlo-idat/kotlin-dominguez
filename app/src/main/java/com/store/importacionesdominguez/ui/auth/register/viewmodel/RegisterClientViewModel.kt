package com.store.importacionesdominguez.ui.auth.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.model.Result
import com.store.importacionesdominguez.data.repository.ClienteRepository
import com.store.importacionesdominguez.utils.validation.EmpleadoValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterClientViewModel
@Inject constructor(
    private val clienteRepository: ClienteRepository,
    private val empleadoValidator: EmpleadoValidator
) : ViewModel() {

    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> get() = _registroExitoso

    suspend fun getClientes() {
        return try {
            val clientes = clienteRepository.getClientes()
            println("Clientes: $clientes")
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun getCliente(id: String) {
        return try {
            EmpleadoValidator.validarId(id)
            val cliente = clienteRepository.getClienteById(id)
            println("Cliente: $cliente")
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    fun createCliente(cliente: ClienteModel) {
        viewModelScope.launch {
            when (val result = clienteRepository.createCliente(cliente)) {
                is Result.Success -> {
                    _registroExitoso.value = true
                    println("Cliente creado: $cliente")
                }

                is Result.Error -> {
                    _registroExitoso.value = false
                    println("Error: ${result.message}")
                }
            }
        }
    }

    suspend fun updateCliente(cliente: ClienteModel, id: String) {
        return try {
            EmpleadoValidator.validarId(id)
            empleadoValidator.validarEmpleado(cliente)
            clienteRepository.getClienteById(id).let {
                val updatedCliente = clienteRepository.updateCliente(cliente, id)
                println("Cliente actualizado: $updatedCliente")
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun deleteCliente(id: String) {
        EmpleadoValidator.validarId(id)
        return try {
            val deletedCliente = clienteRepository.deleteCliente(id)
            println("Cliente: $deletedCliente")
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}