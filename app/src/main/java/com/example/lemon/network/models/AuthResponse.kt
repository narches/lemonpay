package com.example.lemon.network.models


import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val name: String,
    val email: String,
    val phoneNumber: String
)

