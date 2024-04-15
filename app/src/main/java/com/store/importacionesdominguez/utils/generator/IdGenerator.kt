package com.store.importacionesdominguez.utils.generator

import dagger.Component
import java.text.Normalizer
import java.util.Locale
import kotlin.random.Random

object IdGenerator {
    private val random = Random

    fun generarID(campo1: String, campo2: String): String {
        val textASCII = formatoASCII(campo1.trim())
        val text2ASCII = formatoASCII(campo2.trim())

        val text1 = obtenerParteId(textASCII)
        val text2 = obtenerParteId(text2ASCII)
        val cadenaAleatoria = generarCadenaAleatoria(6)
        return "${text1.lowercase()}-$text2-$cadenaAleatoria"
    }

    private fun obtenerParteId(texto: String): String {
        return if (texto.length >= 3) {
            texto.substring(0, 3).lowercase(Locale.ROOT).trim()
        } else {
            texto.lowercase(Locale.ROOT).trim()
        }
    }

    private fun formatoASCII(texto: String): String {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+", "")
    }

    private fun generarCadenaAleatoria(longitud: Int): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val cadenaAleatoria = StringBuilder()
        repeat(longitud) {
            val indice = random.nextInt(caracteres.length)
            cadenaAleatoria.append(caracteres[indice])
        }
        return cadenaAleatoria.toString()
    }
}
