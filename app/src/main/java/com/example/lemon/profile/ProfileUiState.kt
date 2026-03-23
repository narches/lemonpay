package com.example.lemon.profile


import java.math.BigDecimal

sealed interface TransferUiState {

    data class Form(
        val phone: String = "",
        val amount: String = "",
        val description: String = ""
    ) : TransferUiState

    data class Summary(
        val phone: String,
        val amount: String,
        val description: String
    ) : TransferUiState

    data object Loading : TransferUiState

    data class Success(
        val reference: String,
        val phone: String,
        val amount: String,
        val description: String,
        val timestamp: String
    ) : TransferUiState
}