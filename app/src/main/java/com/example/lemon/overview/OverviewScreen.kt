


package com.example.lemon.overview

import android.accounts.Account
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemon.network.DTO.DashboardDTO
import com.example.lemon.network.models.DashboardResponse
import com.example.lemon.utils.MoneyFormatter
import java.math.BigDecimal

@Composable
fun OverviewScreen(
    dashboard: DashboardDTO,
    isBalanceVisible: Boolean,
    onToggleBalance: () -> Unit
) {
    Column {
        BalanceCard(
            balance = dashboard.balance,
            currencyCode = "NGN", // or from backend
            isVisible = isBalanceVisible,
            onToggleVisibility = onToggleBalance,
            Account = dashboard.accountId
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun BalanceCard(
    balance: BigDecimal,
    Account: String,
    currencyCode: String,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
) {
    val formattedBalance = remember(balance, currencyCode) {
        MoneyFormatter.format(
            amount = balance,
            currencyCode = currencyCode
        )
    }

    Spacer(Modifier.height(20.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFFF4CE14),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Available Balance",
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isVisible) formattedBalance else "*******",
                        fontSize = 15.sp,
                        color = Color.Black,
                        style = MaterialTheme.typography.h5
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    androidx.compose.material.Icon(
                        imageVector = if (isVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = "Toggle balance visibility",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(15.dp)
                            .clickable { onToggleVisibility() }
                    )
                }
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Account Number",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Account,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.h5
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                }
            }

        }
    }
}
