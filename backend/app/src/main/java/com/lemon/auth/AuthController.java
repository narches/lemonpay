

package com.lemon.auth;

import com.lemon.auth.dto.AuthResponse;
import com.lemon.auth.dto.LoginRequest;
import com.lemon.auth.dto.RegisterRequest;
import com.lemon.models.User;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.lemon.repo.UserRepository;
import com.lemon.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {

    private final AuthService authService;


    // ---------------- LOGIN ----------------

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    // ---------------- REGISTER ----------------

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {


        return ResponseEntity.ok(authService.register(request));
    }

}

