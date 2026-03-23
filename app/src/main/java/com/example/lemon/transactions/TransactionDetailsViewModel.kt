package com.example.lemon.transactions

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lemon.model.TransactionType
import com.example.lemon.network.models.TransactionDTO



@Composable
fun TransactionItem(
    transaction: TransactionDTO,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = transaction.type.name.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = transaction.createdAt,
                style = MaterialTheme.typography.caption
            )
        }

        Text(
            text = formatAmount(transaction),
            style = MaterialTheme.typography.body1
        )
    }
}


private fun formatAmount(tx: TransactionDTO): String {
    val amount = "₦%,.2f".format(tx.amount.toDouble())

    return when (tx.type) {
        TransactionType.TRANSFER ->
            if (tx.debitPhone == tx.creditPhone) amount
            else "-$amount"

        TransactionType.FUND ->
            "+$amount"

        TransactionType.WITHDRAW ->
            "-$amount"
    }
}