package com.store.importacionesdominguez.utils.validation

import java.math.BigDecimal

object Validations {

    fun isBlank(value: String?): Boolean {
        return value == null || value.trim().isEmpty()
    }

    fun isValidNames(value: String?): Boolean {
        return value != null && !value.matches(".*\\d.*".toRegex())
    }

    fun isValidEmail(email: String?): Boolean {
        return email != null && email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex())
    }

    fun isValidTelephone(value: String?): Boolean {
        return value != null && !value.matches("^[0-9]{9}$".toRegex())
    }

    fun isValidBigDecimal(value: BigDecimal?): Boolean {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0 && isBigDecimalFormatValid(value.toString())
    }

    fun isValidNumber(value: Int): Boolean {
        return value.toString().matches("^[0-9]+$".toRegex())
    }

    fun isValidStock(value: Int): Boolean {
        return value > 0
    }

    fun isValidRUC(value: String?): Boolean {
        return value != null && value.matches("^(20|10)\\d{9}$".toRegex())
    }

    fun isValidDNI(value: String?): Boolean {
        return value != null && value.matches("^\\d{8}$".toRegex())
    }

    private fun isBigDecimalFormatValid(value: String?): Boolean {
        return try {
            BigDecimal(value)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun isValidPassword(value: String?): Boolean {
        return value != null && value.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex())
    }
}