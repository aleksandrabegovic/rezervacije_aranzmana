package com.mycompany.njtrezervacijearanzmana.controller;

import com.mycompany.njtrezervacijearanzmana.dto.impl.*;
import com.mycompany.njtrezervacijearanzmana.servis.AuthServis;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthServis auth;

    public AuthController(AuthServis auth) { this.auth = auth; }

    @PostMapping("/register")
    @Operation(summary = "Registracija zaposlenog")
    public ResponseEntity<ZaposleniDto> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(auth.register(req));
    }

    @PostMapping("/login")
    @Operation(summary = "Login zaposlenog (JWT)")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(auth.login(req));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout (stateless – token se zaboravlja na klijentu)")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("OK");
    }
}
