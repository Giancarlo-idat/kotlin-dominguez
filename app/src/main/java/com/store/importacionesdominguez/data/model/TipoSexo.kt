package com.store.importacionesdominguez.data.model

import java.util.Locale

enum class TipoSexo {
    MASCULINO,
    FEMENINO,
    OTRO;

    fun isValidSexo(value: String): Boolean {
        val upperValue: String = value.uppercase(Locale.ROOT)
        return upperValue == MASCULINO.name
                || upperValue == FEMENINO.name
                || upperValue == OTRO.name
    }
}