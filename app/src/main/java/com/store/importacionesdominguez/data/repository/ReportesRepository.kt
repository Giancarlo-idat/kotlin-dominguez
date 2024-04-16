package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.service.ReportesService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ReportesRepository @Inject constructor(private val reportesService: ReportesService) {

    suspend fun getReportesVentasExcel(): Response<ResponseBody> {
        return reportesService.downloadReportsExcel()
    }

    suspend fun getReportesVentasPDF(): Response<ResponseBody> {
        return reportesService.exportarListadoVentasPdf()
    }
}
