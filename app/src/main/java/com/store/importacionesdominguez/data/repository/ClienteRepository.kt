package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.service.ClienteService
import javax.inject.Inject

class ClienteRepository @Inject constructor(private val clienteService: ClienteService) {

    suspend fun getClientes() = clienteService.getClientes()

    suspend fun getClienteById(id: String) = clienteService.getCliente(id)

    suspend fun createCliente(cliente: ClienteModel) = clienteService.createCliente(cliente)

    suspend fun updateCliente(cliente: ClienteModel, id: String) =
        clienteService.updateCliente(cliente, id)

    suspend fun deleteCliente(id: String) = clienteService.deleteCliente(id)

}