package com.store.importacionesdominguez.utils.generator

import java.time.LocalDate
import java.util.Locale
import kotlin.random.Random

object NumeroCorrelativoGenerator {
    fun generarNumeroCorrelativo(): String {
        val prefijo = "BXC-${LocalDate.now().year}".toUpperCase(Locale.ROOT)
        val numeroCorrelativoExistente: MutableSet<String> = HashSet()
        val random = Random.Default
        val sb = StringBuilder(prefijo)
        repeat(5) {
            sb.append(random.nextInt(10)) // Solo dígitos
        }
        val numeroCorrelativo = sb.toString()
        return numeroCorrelativo.uppercase(Locale.ROOT)
    }

}