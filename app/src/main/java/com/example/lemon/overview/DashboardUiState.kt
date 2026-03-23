package com.example.lemon.overview

import com.example.lemon.network.DTO.DashboardDTO

data class DashboardUiState(
    val isLoading: Boolean = false,
    val dashboard: DashboardDTO? = null,
    val errorMessage: String? = null,
    val userName: String? = null,
    val isBalanceVisible: Boolean = true,
)

