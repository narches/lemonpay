//package com.lemon.service;
//
//import com.lemon.DTO.TransferRequest;
//import com.lemon.DTO.TransferResponse;
//import com.lemon.models.*;
//import com.lemon.repo.AccountRepository;
//import com.lemon.repo.IdempotencyRepository;
//import com.lemon.repo.TransactionRepository;
//import com.lemon.repo.TransactionRequestRepository;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//
//@Service
//public class TransferService {
//
//    private final TransactionService transactionService;
//    private final IdempotencyRepository idempotencyRepository;
//
//    public TransferService(
//            TransactionService transactionService,
//            IdempotencyRepository idempotencyRepository
//    ) {
//        this.transactionService = transactionService;
//        this.idempotencyRepository = idempotencyRepository;
//    }
//
//    public TransferResponse transfer(
//            String senderPhone,
//            TransferRequest request
//    ) {
//
//
//        // 2. Execute domain command
//        TransactionRequest txRequest =
//                transactionService.transfer(
//                        senderPhone,
//                        request.toPhoneNumber(),
//                        request.amount(),
//                        request.description()
//                );
//
//        // 3. Map domain → DTO
//        TransferResponse response = new TransferResponse(
//                txRequest.getReference(),
//                txRequest.getStatus(),
//                txRequest.getAmount(),
//                txRequest.getToPhone(),
//                txRequest.getCompletedAt()
//        );
//
//        // 4. Persist idempotency record
//        idempotencyRepository.save(
//                IdempotencyRecord.builder()
//                        .transactionReference(txRequest.getReference())
//                        .status(txRequest.getStatus())
//                        .build()
//        );
//
//        return response;
//    }
//
//}




package com.lemon.service;

import com.lemon.DTO.TransferRequest;
import com.lemon.DTO.TransferResponse;
import com.lemon.models.TransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final TransactionService transactionService;

    public TransferService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransferResponse transfer(
            String senderPhone,
            TransferRequest request
    ) {

        // 1️⃣ Execute domain command
        TransactionRequest txRequest =
                transactionService.transfer(
                        senderPhone,
                        request.toPhoneNumber(),
                        request.amount(),
                        request.description()
                );

        // 2️⃣ Map DOMAIN → API DTO
        return new TransferResponse(
                txRequest.getReference(),
                txRequest.getStatus(),
                txRequest.getAmount(),
                txRequest.getToPhone(),
                txRequest.getCompletedAt()
        );
    }
}