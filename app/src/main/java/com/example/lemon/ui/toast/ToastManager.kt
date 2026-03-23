

package com.example.lemon.ui.toast




sealed class UiToast {
    data class Error(val message: String) : UiToast()
    data class Success(val message: String) : UiToast()
}
