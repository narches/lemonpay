package com.example.lemon.network

import com.example.lemon.network.models.RegisterRequest
import com.example.lemon.network.models.RegisterResponse
import com.example.lemon.network.models.LoginRequest
import com.example.lemon.network.models.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthAPI {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    companion object {
        fun create(): AuthAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(AuthAPI::class.java)
        }
    }
}
