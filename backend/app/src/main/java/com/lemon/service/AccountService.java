package com.lemon.service;

import com.lemon.models.Account;
import com.lemon.repo.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalStateException("Account not found"));
    }

    public BigDecimal getBalance(String phoneNumber) {
        return getAccount(phoneNumber).getBalance();
    }
}
