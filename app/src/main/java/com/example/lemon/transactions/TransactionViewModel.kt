package com.example.lemon.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemon.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions(isRefresh: Boolean = false) {

        // Prevent parallel loads
        if (_uiState.value.isLoading || _uiState.value.isRefreshing) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )

            runCatching {
                repository.getTransactions()
            }
                .onSuccess { transactions ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        transactions = transactions
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = when (throwable) {
                            is ClientRequestException -> {
                                when (throwable.response.status.value) {
                                    401 -> "Session expired. Please log in again."
                                    else -> "Failed to load transactions."
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

    fun refresh() {
        loadTransactions(isRefresh = true)
    }
}
