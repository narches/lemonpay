

package com.example.lemon.repository

import com.example.lemon.network.AccountApi
import com.example.lemon.network.models.BalanceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository(
    private val api: AccountApi
) {
    suspend fun getBalance(token: String): BalanceResponse {
        return withContext(Dispatchers.IO) {
            api.getBalance("Bearer $token")
        }
    }
}
