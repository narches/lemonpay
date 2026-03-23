//package com.lemon.controllers;
//
//import com.lemon.DTO.TransferRequest;
//import com.lemon.DTO.TransferResponse;
//import com.lemon.service.TransferService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//@RestController
//@RequestMapping("/api")
//public class TransferController {
//
//    private final TransferService transferService;
//
//    public TransferController(TransferService transferService) {
//        this.transferService = transferService;
//    }
//
//    @PostMapping("/transfer")
//    public TransferResponse transfer(
//            Authentication authentication,
//            @Valid @RequestBody TransferRequest request
//    ) {
//
//        // ✅ AUTH GUARD (same as Dashboard)
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED,
//                    "Authentication required"
//            );
//        }
//
//        String fromPhone = authentication.getName(); // JWT subject
//
////        return transferService.transfer(
//                fromPhone,
//                request.toPhoneNumber(),
//                request.amount(),
//                request.description()
//        );
//    }
//}


package com.lemon.controllers;

import com.lemon.DTO.TransferRequest;
import com.lemon.DTO.TransferResponse;
import com.lemon.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(
            Authentication authentication,
            @Valid @RequestBody TransferRequest request
    ) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }

        String fromPhone = authentication.getName(); // JWT subject
        return transferService.transfer(fromPhone, request);
    }
}