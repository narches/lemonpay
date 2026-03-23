package com.lemon.service;

import com.lemon.models.*;
import com.lemon.repo.AccountRepository;
import com.lemon.repo.TransactionRepository;
import com.lemon.repo.TransactionRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionRequestRepository requestRepository;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            TransactionRequestRepository requestRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.requestRepository = requestRepository;
    }

    /**
     * COMMAND: Transfer funds
     */
    @Transactional
    public TransactionRequest transfer(
            String fromPhone,
            String toPhone,
            BigDecimal amount,
            String description
    ) {

        // 1. Create request (aggregate root)
        TransactionRequest request = new TransactionRequest(
                TransactionType.TRANSFER,
                amount,
                fromPhone,
                toPhone
        );
        requestRepository.save(request);

        try {
            // 2. Load accounts
            Account sender = loadAccount(fromPhone, "Sender not found");
            Account receiver = loadAccount(toPhone, "Receiver not found");

            // 3. Balance mutation
            performTransfer(sender, receiver, amount);

            // 4. SINGLE immutable ledger transaction
            Transaction ledger = new Transaction(
                    request.getReference(),           // single source of truth
                    TransactionType.TRANSFER,
                    amount,
                    fromPhone,
                    toPhone,
                    description != null
                            ? description
                            : "Transfer to " + toPhone
            );
            transactionRepository.save(ledger);

            // 5. Persist accounts
            accountRepository.save(sender);
            accountRepository.save(receiver);

            // 6. Finalize request
            request.markCompleted();
            requestRepository.save(request);

            return request;

        } catch (Exception ex) {
            failRequest(request, ex);
            throw ex;
        }
    }

    /* =========================
       DOMAIN HELPERS (KEEP)
       ========================= */

    private Account loadAccount(String phoneNumber, String errorMessage) {
        return accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalStateException(errorMessage));
    }

    private void performTransfer(
            Account sender,
            Account receiver,
            BigDecimal amount
    ) {
        sender.debit(amount);
        receiver.credit(amount);
    }

    private void failRequest(
            TransactionRequest request,
            Exception exception
    ) {
        request.markFailed(exception.getMessage());
        requestRepository.save(request);
    }
}