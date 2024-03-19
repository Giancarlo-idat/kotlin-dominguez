package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.model.TipoDocumentoIdentidadModel
import com.store.importacionesdominguez.data.service.TipoDocumentoIdentidadService
import javax.inject.Inject

class TipoDocumentoIdentidadRepository
    @Inject constructor(private val tipoDocumentoService: TipoDocumentoIdentidadService) {

    suspend fun getTipoDocumentoIdentidades() = tipoDocumentoService.getTipoDocumentos()

    suspend fun getTipoDocumentoIdentidad(id: String) = tipoDocumentoService.getTipoDocumento(id)

    suspend fun createTipoDocumentoIdentidad(tipoDocumento: TipoDocumentoIdentidadModel) = tipoDocumentoService.createTipoDocumento(tipoDocumento)

    suspend fun updateTipoDocumentoIdentidad(tipoDocumento: TipoDocumentoIdentidadModel, id: String) = tipoDocumentoService.updateTipoDocumento(tipoDocumento, id)

    suspend fun deleteTipoDocumentoIdentidad(id: String) = tipoDocumentoService.deleteTipoDocumento(id)

}