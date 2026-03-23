


package com.example.lemon.network

import com.example.lemon.network.models.DashboardResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getDashboard(): DashboardResponse =
        client.get("api/dashboard").body()
}
