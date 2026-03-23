package com.lemon.service;

import com.lemon.DTO.DashboardResponse;
import com.lemon.models.Account;
import com.lemon.models.Transaction;
import com.lemon.repo.AccountRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.lemon.repo.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public DashboardService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public DashboardResponse getDashboard(String phoneNumber) {

        Account account = accountRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalStateException("Account not found"));

        List<Transaction> transactions =
                transactionRepository.findForAccount(
                        phoneNumber,
                        PageRequest.of(
                                0,
                                10,
                                Sort.by(Sort.Direction.DESC, "createdAt")
                        )
                );

        return new DashboardResponse(
                account.getName(),
                account.getPhoneNumber(),
                account.getBalance(),
                transactions
        );
    }
}