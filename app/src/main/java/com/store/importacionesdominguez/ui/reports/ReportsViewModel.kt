package com.store.importacionesdominguez.ui.reports

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.repository.ReportesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsRepository: ReportesRepository
) : ViewModel() {

    fun downloadReportsExcel() {
        viewModelScope.launch {
            try {
                val response = reportsRepository.getReportesVentasExcel()
                if (response.isSuccessful) {
                    saveExcelFile(response.body())
                } else {
                    Log.e("ReportsViewModel", "Error al descargar el archivo: ${response.code()}")
                    // Manejar el error de descarga según sea necesario
                }
            } catch (e: Exception) {
                Log.e("ReportsViewModel", "Error al descargar el archivo: ${e.message}")
                // Manejar el error de descarga según sea necesario
            }
        }
    }

    private suspend fun saveExcelFile(body: ResponseBody?) {
        withContext(Dispatchers.IO) {
            try {
                body?.let {
                    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "reports.xlsx")
                    val inputStream = it.byteStream()
                    val outputStream = FileOutputStream(file)
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    outputStream.close()
                    inputStream.close()
                    // Manejar la descarga exitosa según sea necesario
                }
            } catch (e: Exception) {
                Log.e("ReportsViewModel", "Error al guardar el archivo Excel: ${e.message}")
                // Manejar el error de guardar el archivo según sea necesario
            }
        }
    }

}
