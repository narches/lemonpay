package com.example.lemon.network.models


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal


@Serializable
data class TransferRequest(

    val toPhoneNumber: String,
    val amount: String,
    val description: String?,
)
