
package com.example.lemon.auth

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lemon.navigation.Routes
import com.example.lemon.ui.toast.ToastManager

@Composable
fun RegistrationRoute(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()

) {

    val loading by viewModel.loading.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { toast ->
            ToastManager.show(toast)
        }
    }

    /* ---- Handle navigation side-effect ---- */
    LaunchedEffect(Unit) {
        viewModel.registerSuccess.collect {
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.REGISTER) { inclusive = true }
            }

        }
    }




    RegistrationScreen(
        loading = loading,
        onRegister = { name, email, phone, password ->
            viewModel.register(name, email, phone, password)
        },
        onBack = {navController.popBackStack()}
    )
}
