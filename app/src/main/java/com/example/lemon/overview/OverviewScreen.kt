

package com.example.lemon.overview

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OverviewScreen(
    phoneNumber: String,
    viewModel: OverviewViewModel = viewModel()
) {
    val balance by viewModel.balance.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadBalance(phoneNumber)
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshing),
        onRefresh = { viewModel.loadBalance(phoneNumber) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Available Balance", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(12.dp))
            Text(
                text = "₦${"%,.2f".format(balance)}",
                style = MaterialTheme.typography.h4
            )
        }
    }
}
