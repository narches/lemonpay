package com.example.lemon.network

import com.example.lemon.network.models.AuthResponse
import com.example.lemon.network.models.LoginRequest
import com.example.lemon.network.models.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun login(request: LoginRequest): AuthResponse =
        client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<AuthResponse>()

    suspend fun register(request: RegisterRequest): AuthResponse =
        client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<AuthResponse>()
}
