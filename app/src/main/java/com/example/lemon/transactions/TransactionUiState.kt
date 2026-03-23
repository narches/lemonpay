package com.example.lemon.transactions

import com.example.lemon.network.DTO.TransactionDTO



data class TransactionUiState(
    val transactions: List<TransactionDTO> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
