package com.example.lemon.profile



import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lemon.profile.ui.ProfileScreen

@Composable
fun ProfileRoute(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    /* ---------- ONE-OFF EVENTS ---------- */

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProfileEvent.Toast ->
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()

                ProfileEvent.Logout ->
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
            }
        }
    }

    /* ---------- UI STATE ---------- */

    when (val state = uiState) {
        ProfileUiState.Loading -> Unit

        is ProfileUiState.Success ->
            ProfileScreen(
                profile = state.profile,
                onBack = {
                    if (!navController.popBackStack()) {
                        navController.navigate("home")
                    }
                },
                onLogout = viewModel::logout
            )

        is ProfileUiState.Error -> {
            LaunchedEffect(state.message) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}