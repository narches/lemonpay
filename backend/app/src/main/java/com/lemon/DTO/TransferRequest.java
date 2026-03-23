package com.lemon.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;



public record TransferRequest(
        String toPhoneNumber,
        @NotNull(message = "Amount required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,
        String description
) {}
