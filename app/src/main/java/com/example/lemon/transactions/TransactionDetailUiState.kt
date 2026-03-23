package com.example.lemon.transactions

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lemon.model.TransactionDTO
import com.example.lemon.model.TransactionDirection
import com.example.lemon.network.DTO.TransactionDTO

@Composable
fun TransactionRow(
    transaction: TransactionDTO,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        text = { Text(transaction.description ?: "Transaction") },
        secondaryText = { Text(transaction.createdAt) },
        trailing = {
            Text(
                text = transaction.amount.toPlainString(),
                color = if (transaction.direction == TransactionDirection.OUT)
                    MaterialTheme.colors.error
                else
                    MaterialTheme.colors.primary
            )
        }
    )
}
