
package com.example.lemon.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OverviewRoute(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    // 1️⃣ Collect state FIRST
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 2️⃣ Snapshot delegated state (required)
    val state = uiState

    // 3️⃣ Load data once
    LaunchedEffect(Unit) {
        viewModel.loadDashboard()
    }

    // 4️⃣ Render UI
    when {
        state.isLoading -> {
            LoadingContent()
        }

        state.errorMessage != null -> {
            ErrorContent(
                message = state.errorMessage,
                onRetry = viewModel::loadDashboard
            )
        }

        state.dashboard != null ->
            OverviewScreen(
                dashboard = state.dashboard,
                isBalanceVisible = state.isBalanceVisible,
                onToggleBalance = viewModel::toggleBalanceVisibility
            )

        else -> {
            LoadingContent(message = "Preparing dashboard…")
        }
    }
}
