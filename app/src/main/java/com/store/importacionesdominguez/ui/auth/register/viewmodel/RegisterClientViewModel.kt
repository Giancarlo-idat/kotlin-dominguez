package com.store.importacionesdominguez.ui.auth.register.viewmodel

import androidx.lifecycle.ViewModel
import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.repository.ClienteRepository
import com.store.importacionesdominguez.utils.validation.EmpleadoValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterClientViewModel
@Inject constructor(
    private val clienteRepository: ClienteRepository,
    private val empleadoValidator: EmpleadoValidator
) : ViewModel() {

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

    suspend fun createCliente(cliente: ClienteModel) {
        return try {
            empleadoValidator.validarEmpleado(cliente)
            val newCliente = clienteRepository.createCliente(cliente)
            println("Cliente: $newCliente")
        } catch (e: Exception) {
            println("Error: $e")
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