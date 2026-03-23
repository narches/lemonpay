

package com.example.lemon.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemon.repository.DashboardRepository
import com.example.lemon.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: DashboardRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(DashboardUiState(userName = sessionManager.getUserName()))
    val uiState = _uiState.asStateFlow()

    fun loadDashboard() {
        // Prevent parallel calls
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching { repository.getDashboard() }
                .onSuccess { dashboard ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        dashboard = dashboard
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = when (throwable) {
                            is ClientRequestException -> {
                                when (throwable.response.status.value) {
                                    401 -> "Session expired. Please log in again."
                                    else -> "Request failed. Please try again."
                                }
                            }

                            is ServerResponseException ->
                                "Server error. Try again later."

                            else ->
                                throwable.message ?: "Unexpected error"
                        }
                    )
                }
        }
    }

    fun toggleBalanceVisibility() {
        _uiState.value = _uiState.value.copy(
            isBalanceVisible = !_uiState.value.isBalanceVisible
        )
    }
}
