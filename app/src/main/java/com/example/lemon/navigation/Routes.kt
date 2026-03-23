
package com.example.lemon.mappers

import com.example.lemon.network.models.TransactionResponse
import com.example.lemon.model.TransactionUiModel
import com.example.lemon.model.*




fun TransactionResponse.toUiModel(): TransactionUiModel {
    return TransactionUiModel(
        id = id,
        type = TransactionType.valueOf(type),
        amount = amount,
        fromAccount = fromAccount,
        toAccount = toAccount,
        status = TransactionStatus.valueOf(status),
        description = description,
        timestamp = timestamp
    )
}
