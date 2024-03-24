package com.store.importacionesdominguez.ui.tipoDocumento.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store.importacionesdominguez.data.model.Result
import com.store.importacionesdominguez.data.model.TipoDocumentoIdentidadModel
import com.store.importacionesdominguez.data.repository.TipoDocumentoIdentidadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TipoDocumentoViewModel @Inject constructor(
    private val documentoRepository: TipoDocumentoIdentidadRepository
) :ViewModel(){

    private val _tiposDocumentos = MutableStateFlow<List<TipoDocumentoIdentidadModel>>(emptyList())
    val tiposDocumentos: StateFlow<List<TipoDocumentoIdentidadModel>> = _tiposDocumentos


    fun getTipoDocumentos () {
        viewModelScope.launch {
            try {
                val tipoDocumentoListResponse = documentoRepository.getTipoDocumentoIdentidades()
                _tiposDocumentos.value = tipoDocumentoListResponse
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }


}