
package com.example.lemon.mappers

import com.example.lemon.network.models.TransactionResponse
import com.example.lemon.model.*
import com.example.lemon.network.DTO.TransactionDTO
import com.example.lemon.model.TransactionDirection


fun TransactionResponse.toDTO(myPhone: String): TransactionDTO {


    return TransactionDTO(
        id = id,
        reference = reference,
        type = type,
        amount = amount,
        debitPhone = debitPhone,
        status = status,
        creditPhone = creditPhone,
        description = description ?: "Transaction",
        createdAt = createdAt,
    )
}