

package com.example.lemon.repository

import com.example.lemon.network.AccountAPI
import com.example.lemon.network.models.BalanceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository(
    private val api: AccountAPI
) {
    suspend fun getBalance(token: String): BalanceResponse {
        return withContext(Dispatchers.IO) {
            api.getBalance("Bearer $token")
        }
    }
}
