package com.example.lemon.repository

import com.example.lemon.network.DTO.TransactionDTO
import com.example.lemon.mappers.toDTO
import com.example.lemon.network.TransactionService
import com.example.lemon.session.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val service: TransactionService,
    private val sessionManager: SessionManager,
) {

    suspend fun getTransactions(): List<TransactionDTO> {

        val myPhone = sessionManager.requirePhoneNumber()
        return service
            .getTransactions()
            .map { response ->
                response.toDTO(myPhone)
            }
    }

    suspend fun getTransaction(
        reference: String
    ): TransactionDTO {

        val myPhone = sessionManager.requirePhoneNumber()

        return service
            .getTransaction(reference)
            .toDTO(myPhone)
    }
}
