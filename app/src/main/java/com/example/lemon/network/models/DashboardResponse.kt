package com.example.lemon.network.models

import com.example.lemon.network.DTO.TransactionDTO
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal



@Serializable
data class DashboardResponse(
    val name: String,
    val phoneNumber: String,
   @Contextual
    val balance: BigDecimal,
    val recentTransactions: List<TransactionDTO>
)
