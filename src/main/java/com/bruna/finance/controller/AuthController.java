package com.bruna.finance.controller;

import com.bruna.finance.dto.RegisterRequest;
import com.bruna.finance.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bruna.finance.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
    //esse Metodo recebe email e senha, chama authService.login() gera token e retorna
    @PostMapping("/login")
public ResponseEntity<String> login(@RequestBody LoginRequest request) {

    String token = authService.login(request);

    return ResponseEntity.ok(token);
}
}