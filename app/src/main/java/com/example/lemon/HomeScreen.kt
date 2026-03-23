
package com.example.lemon



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lemon.navigation.Routes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lemon.network.DTO.TransactionDTO
import com.example.lemon.overview.DashboardViewModel
import com.example.lemon.overview.OverviewRoute
import com.example.lemon.utils.WelcomeHeader
import java.math.BigDecimal




@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        backgroundColor = Color(0xFF000000),
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        HomeContent(
            modifier = Modifier.padding(padding),
            navController = navController
        )
    }
}



@Composable
private fun HomeContent(
    modifier: Modifier,
    navController: NavController
) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val uiState by dashboardViewModel.uiState.collectAsState()

    Column(
             modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(10.dp))

        WelcomeHeader(userName = uiState.userName)

        OverviewRoute()

        Spacer(Modifier.height(10.dp))

        QuickActionsRow(navController)

        Spacer(Modifier.height(20.dp))

        uiState.dashboard?.let {dashboard ->
            RecentTransactionsSection(
                transactions = dashboard.recentTransactions,
                onTransactionClick = { tx ->
                    navController.navigate("${Routes.TRANSACTION_DETAIL}/${tx.reference}")
                },
                onSeeAllClick = {
                    navController.navigate(Routes.TRANSACTIONS)
                }
            )
        }
    }
}



//RECENT TRANSACTIONS FLOW
@Composable
private fun RecentTransactionsSection(
    transactions: List<TransactionDTO>,
    onTransactionClick: (TransactionDTO) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recent Transactions",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "See all",
                color = Color(0xFFF4CE14),
                modifier = Modifier.clickable(onClick = onSeeAllClick)
            )
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(transactions.take(5)) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { onTransactionClick(transaction) }
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

//TRANSACTION ITEM
@Composable
private fun TransactionItem(
    transaction: TransactionDTO,
    onClick: () -> Unit
) {
    val isCredit = transaction.amount > BigDecimal.ZERO

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = transaction.description ?: "Transaction",
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                text = transaction.createdAt,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 11.sp
            )
        }

        Text(
            text = transaction.amount.toPlainString(),
            color = if (isCredit) Color(0xFF4CAF50) else Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun QuickActionsRow(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickActionItem(
            label = "Fund",
            icon = Icons.Default.Add
        ) { navController.navigate(Routes.FUND) }

        QuickActionItem(
            label = "Transfer",
            icon = Icons.Default.Send
        ) { navController.navigate(Routes.TRANSFER) }

        QuickActionItem(
            label = "Withdraw",
            icon = Icons.Default.RemoveCircleOutline
        ) { navController.navigate(Routes.WITHDRAW) }
    }
}

@Composable
private fun QuickActionItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color( 0xFFF4CE14)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF000000)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = Color(0xFFF4CE14)
        )
    }
}


//BOTTOM NAVIGATION
@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color(0xFF000000),
        contentColor = Color(0xFFF4CE14)
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = { navController.navigate(Routes.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selectedContentColor = Color(0xFFF4CE14),
            unselectedContentColor = Color(0xFFF4CE14).copy(alpha = 0.6f)
        )

        BottomNavigationItem(
            selected = false,
            onClick = { navController.navigate(Routes.MENU) },
            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
            label = { Text("Menu") },
            selectedContentColor = Color(0xFFF4CE14),
            unselectedContentColor = Color(0xFFF4CE14).copy(alpha = 0.6f)
        )

        BottomNavigationItem(
            selected = false,
            onClick = { navController.navigate(Routes.USER) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selectedContentColor = Color(0xFFF4CE14),
            unselectedContentColor = Color(0xFFF4CE14).copy(alpha = 0.6f)
        )
    }
}












































































































