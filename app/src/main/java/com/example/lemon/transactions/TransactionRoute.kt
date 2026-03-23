

package com.example.lemon.transactions

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TransactionDetailRoute(
    reference: String,
    onBack: () -> Unit,
    viewModel: TransactionDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(reference) {
        viewModel.load(reference)
    }

    TransactionDetailScreen(
        uiState = uiState,
        onBack = onBack
    )
}
