package com.lemon.repo;

import com.lemon.models.TransactionRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface TransactionRequestRepository
        extends MongoRepository<TransactionRequest, String> {

}