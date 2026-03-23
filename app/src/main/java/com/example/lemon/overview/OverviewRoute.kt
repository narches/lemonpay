package com.example.lemon.overview

import com.example.lemon.network.models.DashboardResponse

data class DashboardUiState(
    val isLoading: Boolean = false,
    val dashboard: DashboardResponse? = null,
    val errorMessage: String? = null,
    val isBalanceVisible: Boolean = true
)

