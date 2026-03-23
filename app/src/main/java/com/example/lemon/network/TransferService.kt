


package com.example.lemon.network

import com.example.lemon.network.models.DashboardResponse
import com.example.lemon.network.models.TransferRequest
import com.example.lemon.network.models.TransferResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun transfer(request: TransferRequest
    ): TransferResponse =
        client.post("/api/transfer")
        {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
