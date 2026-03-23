

package com.example.lemon.ui.toast


import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


object ToastManager {
    private val _events = MutableSharedFlow<UiToast>()
    val events = _events.asSharedFlow()

    suspend fun show(toast: UiToast) {
        _events.emit(toast)

    }
}
