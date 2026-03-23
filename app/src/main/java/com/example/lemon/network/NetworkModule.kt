package com.example.lemon.network

import android.content.Context
import com.example.lemon.network.serialization.BigDecimalSerializer
import com.example.lemon.repository.AuthRepository
import com.example.lemon.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.math.BigDecimal
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:9000/"

    /* ---------------- SESSION ---------------- */



    @Module
    @InstallIn(SingletonComponent::class)
    object AuthModule {

        @Provides
        @Singleton
        fun provideAuthRepository(
            authService: AuthService,
            sessionManager: SessionManager
        ): AuthRepository {
            return AuthRepository(authService, sessionManager)
        }
    }

    @Provides
    @Singleton
    fun provideSessionManager(
        @ApplicationContext context: Context
    ): SessionManager =
        SessionManager(context)

    /* ---------------- HTTP CLIENT ---------------- */

    @Provides
    @Singleton
    fun provideHttpClient(
        sessionManager: SessionManager
    ): HttpClient =
        HttpClient(Android) {

            /* ---------- BASE CONFIG ---------- */
            defaultRequest {
                url(BASE_URL)

                sessionManager.getAccessToken()?.let { token ->
                    header(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            /* ---------- JSON ---------- */
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true

                        serializersModule = SerializersModule {
                            contextual(BigDecimal::class, BigDecimalSerializer)
                        }
                    }
                )
            }

            /* ---------- DO NOT THROW ON 4xx ---------- */
            expectSuccess = false
        }
}
