package com.lemon.controllers;

import com.lemon.DTO.TransferRequest;
import com.lemon.models.TransactionRequest;
import com.lemon.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction-requests")
public class TransactionRequestController {

    private final TransactionService transactionService;

    public TransactionRequestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

}