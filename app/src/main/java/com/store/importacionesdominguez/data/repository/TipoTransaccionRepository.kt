package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.service.TipoTransaccionService
import javax.inject.Inject

class TipoTransaccionRepository @Inject constructor(
    private val tipoTransaccionService: TipoTransaccionService
){
    suspend fun getTipoTransacciones() = tipoTransaccionService.getTipoTransaccion()

 }