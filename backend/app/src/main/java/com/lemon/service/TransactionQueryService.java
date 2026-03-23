package com.lemon.service;

import com.lemon.DTO.TransactionResponse;
import com.lemon.models.Transaction;
import com.lemon.models.TransactionDirection;
import com.lemon.repo.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionQueryService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final TransactionRepository transactionRepository;

    public TransactionQueryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Fetch recent transactions for authenticated account.
     */
    public List<TransactionResponse> getRecentTransactions(String phoneNumber) {

        List<Transaction> transactions =
                transactionRepository.findForAccount(
                        phoneNumber,
                        PageRequest.of(
                                0,
                                DEFAULT_PAGE_SIZE,
                                Sort.by(Sort.Direction.DESC, "createdAt")
                        )
                );

        return transactions
                .stream()
                .map(tx -> toResponse(tx, phoneNumber))
                .toList();
    }

    /**
     * Fetch a single transaction by reference
     * and ensure ownership.
     */
    public TransactionResponse getTransactionByReference(
            String phoneNumber,
            String reference
    ) {
        Transaction tx = transactionRepository
                .findByReference(reference, phoneNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Transaction not found")
                );

        ensureParticipant(tx, phoneNumber);

        return toResponse(tx, phoneNumber);
    }

    /* =========================
       Internal helpers
       ========================= */

    private void ensureParticipant(Transaction tx, String phoneNumber) {
        boolean isParticipant =
                phoneNumber.equals(tx.getDebitPhone()) ||
                        phoneNumber.equals(tx.getCreditPhone());

        if (!isParticipant) {
            throw new IllegalStateException("Access denied");
        }
    }

    private TransactionResponse toResponse(
            Transaction tx,
            String requesterPhone
    ) {
        TransactionDirection direction =
                requesterPhone.equals(tx.getDebitPhone())
                        ? TransactionDirection.OUT
                        : TransactionDirection.IN;

        return new TransactionResponse(
                tx.getId(),
                tx.getReference(),
                tx.getType(),
                tx.getStatus(),
                tx.getAmount(),
                tx.getDebitPhone(),
                tx.getCreditPhone(),
                tx.getDescription(),
                tx.getCreatedAt().toString(),
                direction
        );
    }
}