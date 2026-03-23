package com.example.lemon.profile

import com.example.lemon.profile.model.UserProfileUiModel

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val profile: UserProfileUiModel) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}