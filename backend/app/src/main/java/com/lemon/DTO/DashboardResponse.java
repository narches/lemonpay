package com.lemon.DTO;

import com.lemon.models.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        String name,
        String phoneNumber,
        BigDecimal balance,
        List<Transaction> recentTransactions
) {}