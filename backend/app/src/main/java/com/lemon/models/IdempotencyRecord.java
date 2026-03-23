package com.lemon.models;

import com.lemon.DTO.TransferResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "idempotency_records")
public class IdempotencyRecord {

    @Id
    private String id;

    @Indexed(unique = true)
    private String idempotencyKey;

    private TransferResponse response;

    private String transactionReference;

    private TransactionStatus status;

    @Builder.Default
    private Instant createdAt = Instant.now();

    }