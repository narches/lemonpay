package com.example.lemon.auth

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lemon.navigation.Routes
import com.example.lemon.ui.toast.ToastManager

@Composable
fun LoginRoute(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val loading by viewModel.loading.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { toast ->
            ToastManager.show(toast)
        }
    }

    // 🚀 Navigation collector
    LaunchedEffect(Unit) {
        viewModel.loginSuccess.collect {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }



    LoginScreen(
        loading = loading,
        onLogin = { phone, password ->
            viewModel.login(phone, password)
        },
        onNavigateToRegister = {
            navController.navigate(Routes.REGISTER)
        },
        onBack = {navController.popBackStack()}
    )
}

