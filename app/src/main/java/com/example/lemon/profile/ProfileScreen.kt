package com.example.lemon.profile

import androidx.compose.runtime.Composable
import com.example.lemon.profile.model.UserProfileUiModel

@Composable
fun ProfileScreen(
    profile: UserProfileUiModel,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onEditPhone: () -> Unit,
    onEditEmail: () -> Unit
) {
    // Uses the ProfileScreen implementation already generated
    // Header + Info card + Logout button
}