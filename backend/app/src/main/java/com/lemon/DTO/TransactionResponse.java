package com.lemon.DTO;

import com.lemon.models.TransactionDirection;
import com.lemon.models.TransactionStatus;
import com.lemon.models.TransactionType;

import java.math.BigDecimal;


public record TransactionResponse(
        String id,
        String reference,
        TransactionType type,
        TransactionStatus status,
        BigDecimal amount,
        String debitPhone,
        String creditPhone,
        String description,
        String createdAt,

        TransactionDirection direction) {}