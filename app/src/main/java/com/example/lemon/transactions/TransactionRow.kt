package com.example.lemon.transactions

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.lemon.model.TransactionStatus
import com.example.lemon.network.DTO.TransactionDTO
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionRow(
    transaction: TransactionDTO,
    onClick: () -> Unit
) {
    val LemonYellow = Color(0xFFF4CE14)
    val LemonBlack = Color(0xFF000000)
    val amountColor =
        if (transaction.status == TransactionStatus.COMPLETED)
            MaterialTheme.colors.error
        else
            MaterialTheme.colors.primary

    ListItem(
        modifier = Modifier.clickable(onClick = onClick),

        text = {
            Text(
                text = transaction.description ?: "Transaction",
                style = MaterialTheme.typography.body1,
                        color = LemonYellow


            )
        },

        secondaryText = {
            Text(
                text = transaction.createdAt,
                color = LemonYellow,
                style = MaterialTheme.typography.caption
            )
        },

        trailing = {
            Text(
                text = formatAmount(transaction.amount),
                color = amountColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}

private fun formatAmount(amount: java.math.BigDecimal): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "NG"))
    return formatter.format(amount)
}
