package com.example.lemon.network



import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal



@Serializable
data class TransferDTO(
    val toPhoneNumber: String,
    val amount: String,
    val description: String?,
)

