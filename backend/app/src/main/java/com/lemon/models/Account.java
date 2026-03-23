package com.lemon.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "accounts")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    private String id;

    @Indexed(unique = true)
    @Setter(AccessLevel.NONE)
    private String phoneNumber; // ACCOUNT NUMBER

    private String name;

    private BigDecimal balance = BigDecimal.ZERO;

    private Instant createdAt = Instant.now();

    public Account(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }


    public void credit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        validateAmount(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }
    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
           throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
