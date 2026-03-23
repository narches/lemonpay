package com.example.lemon.network.DTO

import com.example.lemon.model.TransactionStatus
import com.example.lemon.model.TransactionType
import java.math.BigDecimal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TransactionResponse(

    @SerialName("id")
    val id: String,

    @SerialName("reference")
    val reference: String,

    @SerialName("type")
    val type: TransactionType,

    @SerialName("status")
    val status: TransactionStatus,

    @SerialName("amount")
    val amount: BigDecimal,

    @SerialName("debitPhone")
    val debitPhone: String,

    @SerialName("creditPhone")
    val creditPhone: String,

    @SerialName("description")
    val description: String? = null,

    @SerialName("createdAt")
    val createdAt: String
)
