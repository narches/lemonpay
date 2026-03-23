package com.example.lemon.auth

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lemon.navigation.Routes
import com.example.lemon.ui.toast.ToastManager

@Composable
fun LogoutRoute(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    // Trigger logout once
    LaunchedEffect(Unit) {
        viewModel.logout()
    }

    // Listen for logout completion
    LaunchedEffect(Unit) {
        viewModel.logoutSuccess.collect {
            navController.navigate(Routes.INDEX) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Optional toast handling
    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { toast ->
            ToastManager.show(toast)
        }
    }
}