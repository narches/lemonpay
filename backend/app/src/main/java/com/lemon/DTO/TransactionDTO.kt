package com.lemon.DTO;

import com.lemon.models.TransactionDirection
import com.lemon.models.TransactionStatus
import java.math.BigDecimal;
import java.time.Instant;
import com.lemon.models.TransactionType;


data class TransactionDTO(
        val id: String,
        val reference: String,
        val type: TransactionType,
        val status: TransactionStatus,
        val direction: TransactionDirection,
        val amount: BigDecimal,
        val debitPhone: String?,
        val creditPhone: String?,
        val description: String,
        val createdAt: String
)