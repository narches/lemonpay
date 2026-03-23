package com.lemon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Document(collection = "transaction_requests")
@Getter
@NoArgsConstructor
public class TransactionRequest {

    @Id
    private String id;

    @Indexed(unique = true)
    private String reference;


    private TransactionType type;
    private TransactionStatus status;

    private BigDecimal amount;

    private String fromPhone;
    private String toPhone;

    private String failureReason;

    private Instant createdAt;
    private Instant completedAt;


    public TransactionRequest(
            TransactionType type,
            BigDecimal amount,
            String fromPhone,
            String toPhone
    ) {
        validateAmount(amount);
        validateParticipants(type, fromPhone, toPhone);

        this.reference = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.fromPhone = fromPhone;
        this.toPhone = toPhone;
        this.status = TransactionStatus.PENDING;
        this.createdAt = Instant.now();
    }

    /* =========================
       State transitions
       ========================= */

    public void markCompleted() {
        ensurePending();
        this.status = TransactionStatus.COMPLETED;
        this.completedAt = Instant.now();
    }

    public void markFailed(String reason) {
        ensurePending();
        this.status = TransactionStatus.FAILED;
        this.failureReason = reason;
        this.completedAt = Instant.now();
    }


    /* =========================
       Guards
       ========================= */

    private void ensurePending() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException(
                    "TransactionRequest already finalized with status: " + status
            );
        }
    }

    private static void validateIdempotencyKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Idempotency-Key header is required");
        }
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    private static void validateParticipants(
            TransactionType type,
            String fromPhone,
            String toPhone
    ) {
        switch (type) {
            case TRANSFER -> {
                if (fromPhone == null || toPhone == null) {
                    throw new IllegalStateException(
                            "TRANSFER requires both fromPhone and toPhone"
                    );
                }
            }
            case DEPOSIT -> {
                if (toPhone == null) {
                    throw new IllegalStateException(
                            "DEPOSIT requires toPhone"
                    );
                }
            }
            case WITHDRAW -> {
                if (fromPhone == null) {
                    throw new IllegalStateException(
                            "WITHDRAW requires fromPhone"
                    );
                }
            }
            default -> throw new IllegalStateException(
                    "Unsupported transaction type: " + type
            );
        }
    }

}