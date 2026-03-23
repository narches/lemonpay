package com.lemon.controllers;

import com.lemon.DTO.TransactionResponse;
import com.lemon.service.TransactionQueryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionQueryController {

    private final TransactionQueryService transactionQueryService;

    public TransactionQueryController(TransactionQueryService transactionQueryService) {
        this.transactionQueryService = transactionQueryService;
    }

    /**
     * Fetch recent transactions for authenticated account
     */
    @GetMapping
    public List<TransactionResponse> getRecentTransactions(
            Authentication authentication
    ) {
        return transactionQueryService
                .getRecentTransactions(authentication.getName());
    }

    /**
     * Fetch a single transaction by reference
     */
    @GetMapping("/{reference}")
    public TransactionResponse getTransactionByReference(
            Authentication authentication,
            @PathVariable String reference
    ) {
        return transactionQueryService
                .getTransactionByReference(authentication.getName(), reference);
    }
}
