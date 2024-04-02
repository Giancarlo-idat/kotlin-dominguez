package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.model.Result
import com.store.importacionesdominguez.data.service.ClienteService
import javax.inject.Inject

class ClienteRepository @Inject constructor(private val clienteService: ClienteService) {

    suspend fun getClientes() = clienteService.getClientes()

    suspend fun getClienteById(id: String) = clienteService.getCliente(id)

    suspend fun createCliente(cliente: ClienteModel) :Result<ClienteModel> {
        val response = clienteService.createCliente(cliente)
        println("Cliente: $cliente")
        println(response)
        return if(response.isSuccessful) {
            val clienteBody = response.body() ?: throw Exception("Error al crear cliente")
            println("Cliente creado: $clienteBody")
            Result.Success(clienteBody)
        } else {
            println("Error al crear cliente: ${response.message()}")
            Result.Error("Error al crear cliente: ${response.message()}")
        }
    }

    suspend fun updateCliente(cliente: ClienteModel, id: String) =
        clienteService.updateCliente(cliente, id)

    suspend fun deleteCliente(id: String) = clienteService.deleteCliente(id)

}