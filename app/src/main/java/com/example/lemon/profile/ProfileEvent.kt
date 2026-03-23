package com.example.lemon.profile

sealed interface ProfileEvent {
    data class Toast(val message: String) : ProfileEvent
    data object Logout : ProfileEvent
}