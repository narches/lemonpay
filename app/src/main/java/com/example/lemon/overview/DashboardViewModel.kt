package com.example.lemon.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemon.network.AccountApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val api: DashboardApi
) : ViewModel() {

    private val _state = MutableStateFlow<DashboardResponse?>(null)
    val state: StateFlow<DashboardResponse?> = _state

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadDashboard() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = api.getDashboard()
            } finally {
                _loading.value = false
            }
        }
    }
}