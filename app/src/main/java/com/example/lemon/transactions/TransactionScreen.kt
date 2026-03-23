package com.example.lemon.transactions


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lemon.components.SearchBar
import com.example.lemon.model.SearchIntent
import com.example.lemon.network.DTO.TransactionDTO
import com.example.lemon.ui.theme.LemonYellow
import com.example.lemon.utils.detectSearchIntent
import com.google.accompanist.swiperefresh.*



@Composable
fun TransactionScreen(
    onBack: () -> Unit,
    uiState: TransactionUiState,
    onRefresh: () -> Unit,
    onTransactionClick: (TransactionDTO) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredTransactions = remember(
        searchQuery,
        uiState.transactions
    ) {
        if (searchQuery.isBlank()) {
            uiState.transactions
        } else {
            val intent = detectSearchIntent(searchQuery)
            uiState.transactions.filter { tx ->
                when (intent) {
                    SearchIntent.ACCOUNT ->
                        tx.reference.contains(searchQuery, true)

                    SearchIntent.PHONE ->
                        tx.debitPhone.contains(searchQuery, true) ||
                                tx.creditPhone.contains(searchQuery, true)

                    SearchIntent.AMOUNT ->
                        tx.amount.toPlainString().contains(searchQuery)

                    else -> false
                }
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(uiState.isRefreshing),
        onRefresh = onRefresh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000))
                .padding(10.dp)

        ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                Spacer(modifier = Modifier.height(10.dp))

                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator( color = LemonYellow)
                    }

                    uiState.error != null -> {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colors.error
                        )
                    }

                    else -> {
                        LazyColumn {
                            items(filteredTransactions) { tx ->
                                TransactionRow(
                                    transaction = tx,
                                    onClick = {
                                        onTransactionClick(tx)
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
