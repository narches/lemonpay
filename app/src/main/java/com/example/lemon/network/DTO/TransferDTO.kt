package com.example.lemon.network.DTO



import java.math.BigDecimal



data class DashboardDTO(
    val name: String,
    val accountId: String,
    val balance: BigDecimal,
    val recentTransactions: List<TransactionDTO>
)

