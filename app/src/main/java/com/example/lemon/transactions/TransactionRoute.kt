package com.example.lemon.transactions

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lemon.network.DTO.TransactionDTO

@Composable
fun TransactionRoute(
    navController: NavController,
    onTransactionClick: (TransactionDTO) -> Unit,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTransactions()}

    TransactionScreen(
        uiState = uiState,
        onBack = {navController.popBackStack()},
        onRefresh = viewModel::refresh,
        onTransactionClick = onTransactionClick
    )
}
