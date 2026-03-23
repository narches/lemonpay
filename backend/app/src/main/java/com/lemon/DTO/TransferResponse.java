package com.lemon.DTO;

import com.lemon.models.TransactionStatus;
import java.math.BigDecimal;
import java.time.Instant;


public record TransferResponse(
        String reference,
        TransactionStatus status,
        BigDecimal amount,
        String toPhoneNumber,
        Instant createdAt
) {}