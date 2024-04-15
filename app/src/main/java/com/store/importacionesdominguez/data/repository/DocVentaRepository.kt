package com.store.importacionesdominguez.data.repository

import android.util.Log
import com.store.importacionesdominguez.data.model.DocVentaModel
import com.store.importacionesdominguez.data.service.DocVentaService
import javax.inject.Inject
import com.store.importacionesdominguez.data.model.Result

class DocVentaRepository @Inject constructor(
    private val docVentaService: DocVentaService
){

    suspend fun getDocVenta() = docVentaService.getDocVentas()

    suspend fun createDocVenta(docVenta: DocVentaModel): Result<DocVentaModel> {
        return try {
            val response = docVentaService.createDocVenta(docVenta)
            Log.d("DocVentaRepository", "Respuesta de la creación del documento de venta: $response")
            if (response.isSuccessful) {
                val createdDocVenta = response.body() ?: throw  Exception("Error al crear el documento de venta")
                Result.Success(createdDocVenta)
            } else {
                Result.Error("Error al crear el documento de venta")
            }
        } catch (e: Exception) {
            Log.e("DocVentaRepository", "Error al crear el documento de venta: ${e.message}")
            Result.Error("Error al crear el documento de venta: ${e.message}")
        }
    }


}