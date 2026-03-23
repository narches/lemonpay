
package com.example.lemon.network.models
import java.math.BigDecimal




data class DashboardDTO(
    val reference: String,
    val phoneNumber: String,
    val amount: BigDecimal,
    val debitPhone: String,
    val creditPhone: String,
    val createdAt: String,
)
