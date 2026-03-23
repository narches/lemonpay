package com.example.lemon.repository

import com.example.lemon.network.AuthService
import com.example.lemon.network.models.AuthResponse
import com.example.lemon.network.models.LoginRequest
import com.example.lemon.network.models.RegisterRequest
import com.example.lemon.session.SessionManager


class AuthRepository(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) {

    suspend fun login(phone: String, password: String) {
        val response = authService.login(
            LoginRequest(phone, password)
        )
        persistSession(response)
    }

    suspend fun register(name: String, email: String, phone: String, password: String) {
        val response = authService.register(
            RegisterRequest(name, email, phone, password)
        )

        persistSession(response)
    }

    suspend fun logout() {
        sessionManager.clear()
    }

    private fun persistSession(response: AuthResponse){
        // Persist tokens immediately
        sessionManager.saveSession(
            accessToken = response.token,
            email = response.email,
            phoneNumber = response.phoneNumber,
            name = response.name
        )
    }
}

