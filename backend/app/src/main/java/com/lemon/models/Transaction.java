package com.lemon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "transactions")
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    private String id;

    @Indexed(unique = true)
    private String reference;

    private TransactionType type;
    private TransactionStatus status;
    private BigDecimal amount;

    private String debitPhone;
    private String creditPhone;
    private String description;

    private Instant createdAt;

    /* =========================
       PRIVATE CONSTRUCTOR
       ========================= */
    public Transaction(
            String reference,
            TransactionType type,
            BigDecimal amount,
            String debitPhone,
            String creditPhone,
            String description
    ) {
        this.reference = reference;
        this.type = type;
        this.amount = amount;
        this.debitPhone = debitPhone;
        this.creditPhone = creditPhone;
        this.description = description;
        this.status = TransactionStatus.COMPLETED;
        this.createdAt = Instant.now();
    }

    /* =========================
       FACTORIES (DOMAIN API)
       ========================= */

    public static Transaction transfer(
            String reference,
            String fromPhone,
            String toPhone,
            BigDecimal amount,
            String description
    ) {
        return new Transaction(
                reference,
                TransactionType.TRANSFER,
                amount,
                fromPhone,
                toPhone,
                description != null
                        ? description
                        : "Transfer to " + toPhone
        );
    }
}