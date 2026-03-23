package com.example.lemon.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemon.model.TransactionStatus
import com.example.lemon.network.DTO.TransactionDTO


@Composable
fun TransactionDetailScreen(
    uiState: TransactionDetailUiState,
    onRetry: () -> Unit,
    onBack: () -> Unit,

) {
    IconButton(
        onClick = { onBack() },
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color(0xFFFFEB3B)
        )
    }


    Scaffold() { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage!!,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Button(
                        onClick = onRetry,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Retry")
                    }
                }

                uiState.transaction != null -> {
                    TransactionDetailContent( tx = uiState.transaction!!)
                }
            }
        }
    }
}

@Composable
private fun TransactionDetailContent(
    tx: TransactionDTO
) {
    val LemonYellow = Color(0xFFF4CE14)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000)),
            verticalArrangement = Arrangement.Center
        ) {

            Text("Transaction Details", style = MaterialTheme.typography.h6, color = LemonYellow,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(Modifier.height(10.dp))

            Text(
                text = tx.description ?: "Transaction",
                style = MaterialTheme.typography.h6,
                color = LemonYellow,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Reference: ${tx.reference}", color = LemonYellow)
            Text("Type: ${tx.type}", color = LemonYellow)
            Text("Status: ${tx.status}", color = LemonYellow)
            Text("Date: ${tx.createdAt}", color = LemonYellow )

            Text(
                text = "Amount: ₦${tx.amount}",
                color = if (tx.status == TransactionStatus.COMPLETED)
                    MaterialTheme.colors.error
                else
                    MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5
            )

            Divider()

            Text("From: ${tx.debitPhone}", color = LemonYellow)
            Text("To: ${tx.creditPhone}", color = LemonYellow)
        }
    }
}