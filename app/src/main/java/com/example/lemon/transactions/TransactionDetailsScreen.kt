package com.example.lemon.transactions


import com.example.lemon.model.Transaction
import com.example.lemon.model.TransactionUiModel
import android.content.Context
import com.example.lemon.utils.generateTransactionReceipt
import com.example.lemon.utils.toReadableDate

class TransactionDetailsViewScreen {

    fun mapToUiModel(
        transaction: Transaction,
        phoneNumber: String
    ): TransactionUiModel {

        val isOutgoing = transaction.fromAccount == phoneNumber

        return TransactionUiModel(
            reference = transaction.id,
            formattedAmount = if (isOutgoing)
                "- ₦${transaction.amount}"
            else
                "+ ₦${transaction.amount}",

            isOutgoing = isOutgoing,
            counterpartyLabel = if (isOutgoing) "To Account" else "From Account",
            counterpartyValue = if (isOutgoing)
                transaction.toAccount
            else
                transaction.fromAccount,

            description = transaction.description,
            status = transaction.status,
            formattedDate = transaction.timestamp.toReadableDate()
        )
    }

    fun downloadReceipt(
        context: Context,
        transaction: Transaction
    ) {
        generateTransactionReceipt(context, transaction)
    }
}