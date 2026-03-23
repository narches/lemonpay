package com.example.lemon.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lemon.components.SearchBar
import com.example.lemon.model.SearchIntent
import com.example.lemon.utils.detectSearchIntent
import com.google.accompanist.swiperefresh.*

@Composable
fun TransactionScreen(
    onTransactionClick: (String) -> Unit,
    viewModel: TransactionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val filtered = remember(searchQuery, uiState.transactions) {
        if (searchQuery.isBlank()) uiState.transactions
        else {
            val intent = detectSearchIntent(searchQuery)
            uiState.transactions.filter { tx ->
                when (intent) {
                    SearchIntent.ACCOUNT_NUMBER ->
                        tx.debitPhone.contains(searchQuery) ||
                                tx.creditPhone.contains(searchQuery)

                    SearchIntent.AMOUNT ->
                        tx.amount.toPlainString().contains(searchQuery)

                    else -> false
                }
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(uiState.isRefreshing),
        onRefresh = viewModel::refresh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colors.error
                    )
                }

                else -> {
                    LazyColumn {
                        items(filtered) { tx ->
                            TransactionRow(
                                transaction = tx,
                                onClick = {
                                    onTransactionClick(tx.reference)
                                }
                            )
                            Divider()
                        }
                    }
                }
            }
        }
    }
}
