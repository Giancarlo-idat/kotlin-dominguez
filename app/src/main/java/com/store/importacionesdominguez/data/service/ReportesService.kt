package com.store.importacionesdominguez.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming

interface ReportesService {

    @GET("reports/download/excel")
    @Streaming
    suspend fun downloadReportsExcel(): Response<ResponseBody>

    @GET("reportes/exportar-listado-venta-pdf")
    suspend fun exportarListadoVentasPdf(): Response<ResponseBody>
}