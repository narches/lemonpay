package com.lemon.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String phoneNumber;

    private String passwordHash;

    private Instant createdAt;
}