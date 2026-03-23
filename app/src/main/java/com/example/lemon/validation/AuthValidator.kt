
package com.example.lemon.auth

private val EMAIL_REGEX =
    Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

private val NAME_REGEX =
    Regex("^[A-Za-z ]{2,40}$")

fun validateName(name: String): String? {
    return when {
        name.isBlank() ->
            "Name is required"
        !NAME_REGEX.matches(name) ->
            "Name must contain only letters and be at least 2 characters"
        else -> null
    }
}

fun validateEmail(email: String): String? {
    return when {
        email.isBlank() ->
            "Email is required"
        !EMAIL_REGEX.matches(email) ->
            "Enter a valid email address"
        else -> null
    }
}

fun validatePhone(phone: String): String? {
    return when {
        phone.isBlank() ->
            "Phone number is required"
        phone.length != 10 ->
            "Phone number must be exactly 10 digits"
        phone.startsWith("0") ->
            "Phone number must not start with 0"
        phone.startsWith("+") ->
            "Do not include country code"
        !phone.all { it.isDigit() } ->
            "Phone number must contain only digits"
        else -> null
    }
}

fun validatePassword(password: String): String? {
    return when {
        password.isBlank() ->
            "Password is required"
        password.length < 8 ->
            "Password must be at least 8 characters"
        !password.any { it.isDigit() } ->
            "Password must contain at least one number"
        !password.any { it.isUpperCase() } ->
            "Password must contain at least one uppercase letter"
        else -> null
    }
}