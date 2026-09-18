package com.parea.controllers;

import com.parea.controllers.dto.AuthResponse;
import com.parea.controllers.dto.LoginRequest;
import com.parea.controllers.dto.RegisterRequest;
import com.parea.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Con JWT stateless no hay nada que invalidar del lado del servidor.
        // El cliente es responsable de descartar el token tras esta llamada.
        // Este endpoint existe para mantener un contrato de API claro
        // y dejar lugar a una blacklist de tokens si en el futuro hiciera falta.
        return ResponseEntity.noContent().build();
    }
}
