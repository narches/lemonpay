package com.example.lemon.mappers

import com.example.lemon.network.DTO.DashboardDTO
import com.example.lemon.network.models.DashboardResponse

fun DashboardResponse.toDashboardDTO(): DashboardDTO {
    return DashboardDTO(
        name = name,
        accountId = phoneNumber, // ← THIS answers your confusion
        balance = balance,
        recentTransactions = recentTransactions
    )
}