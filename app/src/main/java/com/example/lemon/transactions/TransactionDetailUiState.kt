package com.example.lemon.transactions


import com.example.lemon.network.DTO.TransactionDTO

data class TransactionDetailUiState(
    val transaction: TransactionDTO? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)