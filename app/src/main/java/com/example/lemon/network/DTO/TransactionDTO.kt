package com.example.lemon.network.DTO

import com.example.lemon.model.TransactionDirection
import com.example.lemon.model.TransactionStatus
import com.example.lemon.model.TransactionType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TransactionDTO(
    val id: String,
    val reference: String,
    val type: TransactionType,
    @Contextual
    val amount: BigDecimal,
    val debitPhone: String,
    val creditPhone: String,
    val description: String?,
    val createdAt: String,
    val status: TransactionStatus,
)