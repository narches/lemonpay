

package com.example.lemon.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(

    val phoneNumber: String,
    val password: String
)
