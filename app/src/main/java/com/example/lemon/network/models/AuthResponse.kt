package com.example.lemon.network.models


import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(

    val token: String,
    val name: String,
    val phoneNumber: String
)

