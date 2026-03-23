package com.example.lemon.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.example.lemon.profile.model.UserProfileUiModel
import com.example.lemon.session.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _events =  MutableSharedFlow<ProfileEvent>()
    val events: SharedFlow<ProfileEvent> = _events
    private val _uiState =
        MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value =
                ProfileUiState.Success(
                    UserProfileUiModel(
                        name = sessionManager.requireUserName(),
                        phone = sessionManager.requirePhoneNumber(),
                        email = sessionManager.requireEmail(),
                        accountNumber = sessionManager.requirePhoneNumber()
                    )
                )
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clear()
            _events.emit(ProfileEvent.Logout)
        }

    }
}