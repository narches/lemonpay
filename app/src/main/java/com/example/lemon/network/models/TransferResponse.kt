package com.example.lemon.network.models

import com.example.lemon.model.TransactionStatus
import com.example.lemon.model.TransactionType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TransactionResponse(

    val id: String,

    val reference: String,

    val type: TransactionType,

    val status: TransactionStatus,

    @Contextual
    val amount: BigDecimal,

    val debitPhone: String,

    val creditPhone: String,

    val description: String,
    val createdAt: String
)