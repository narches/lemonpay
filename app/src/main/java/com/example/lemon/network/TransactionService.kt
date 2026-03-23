package com.example.lemon.network

import com.example.lemon.network.models.TransactionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionService @Inject constructor(
    private val client: HttpClient
) {

    // Account is inferred by backend from auth/session
    suspend fun getTransactions(): List<TransactionResponse> =
        client.get("/api/transactions").body()

    suspend fun getTransaction(
        reference: String
    ): TransactionResponse =
        client.get("/api/transactions/$reference").body()
}
