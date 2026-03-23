package com.example.lemon.validation

import java.math.BigDecimal

object AuthValidator {

    fun validateName(name: String): String? {
        return when {
            name.isBlank() -> ""
            name.length < 2 -> "Name must be at least 2 characters"
            !name.matches(Regex("^[A-Za-z ]+$")) ->
                "Name can only contain letters"
            else -> null
        }
    }

    fun validateDescription(name: String): String? {
        return when {
            name.isBlank() -> ""
            name.length < 2 -> "Name must be at least 2 characters"
            !name.matches(Regex("^[A-Za-z ]+$")) ->
                "Name can only contain letters"
            else -> null
        }
    }

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> ""
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Enter a valid email address"
            else -> null
        }
    }

    fun validatePhone(phoneNumber: String): String? {
        return when {
            phoneNumber.isBlank() -> ""
            phoneNumber.length != 11 -> "Phone number must be exactly 11 digits"
            phoneNumber.startsWith("+") -> "Do not include country code"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.length < 8 ->
                ""
            !password.any { it.isUpperCase() } ->
                "Password must contain an uppercase letter"
            !password.any { it.isDigit() } ->
                "Password must contain a number"
            else -> null
        }
    }

    fun validateAmount(input: String): Boolean {
        // 1. Try to parse to BigDecimal
        val decimal = input.toBigDecimalOrNull() ?: return false

        // 2. Numerical check: must be greater than zero
        if (decimal <= BigDecimal.ZERO) return false

        // 3. Precision check: e.g., max 2 decimal places for currency
        if (decimal.scale() > 2) return false

        return true
    }
}