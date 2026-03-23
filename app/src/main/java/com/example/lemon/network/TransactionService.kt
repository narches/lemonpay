
package com.example.lemon.network


import com.example.lemon.network.DTO.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface TransactionAPI {

    @GET("api/transactions")
    suspend fun getTransactions(): List<TransactionResponse>

    @GET("api/transactions/{reference}")
    suspend fun getTransaction(
        @Path("reference") reference: String
    ): TransactionResponse


}
