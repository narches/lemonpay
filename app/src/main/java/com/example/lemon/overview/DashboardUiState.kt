package com.example.lemon.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemon.network.models.DashboardResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.lemon.repository.DashboardRepository


class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DashboardResponse?>(null)
    val state: StateFlow<DashboardResponse?> = _state

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _isBalanceVisible = MutableStateFlow(true)
    val isBalanceVisible: StateFlow<Boolean> = _isBalanceVisible

    fun toggleBalanceVisibility() {
        _isBalanceVisible.value = !_isBalanceVisible.value
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = repository.getDashboard()
            } finally {
                _loading.value = false
            }
        }
    }
}