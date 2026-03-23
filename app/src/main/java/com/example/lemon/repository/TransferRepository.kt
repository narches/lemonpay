package com.example.lemon.repository



import com.example.lemon.network.TransferService
import com.example.lemon.network.models.TransferRequest
import com.example.lemon.network.models.TransferResponse
import java.math.BigDecimal
import javax.inject.Inject


class TransferRepository @Inject constructor(
    private val transferService: TransferService
) {

    suspend fun makeTransfer(
        toPhoneNumber: String,
        amount: String,
        description: String?
    ): TransferResponse =
        transferService.transfer(
            TransferRequest(
                toPhoneNumber = toPhoneNumber,
                amount = amount,
                description = description
            )
        )
}
