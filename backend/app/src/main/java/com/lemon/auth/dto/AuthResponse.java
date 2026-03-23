package com.lemon.auth.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private final String token;
    private final String name;
    private final String email;
    private final String phoneNumber;
}