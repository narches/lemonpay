package com.example.lemon.network.models

import com.example.lemon.model.TransactionStatus
import com.example.lemon.model.TransactionType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TransferResponse(

    val reference: String,
    val status: String,
    val amount: String,
    val toPhoneNumber: String,
    val createdAt: String
)