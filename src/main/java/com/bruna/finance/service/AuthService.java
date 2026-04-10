package com.bruna.finance.service;

import com.bruna.finance.dto.LoginRequest;
import com.bruna.finance.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import com.bruna.finance.dto.RegisterRequest;
import com.bruna.finance.entity.Role;
import com.bruna.finance.entity.User;
import com.bruna.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
    public String login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));

    if (!passwordEncoder.matches(request.getSenha(), user.getSenha())) {
        throw new BadCredentialsException("Email ou senha inválidos");
    }

    return jwtService.generateToken(user.getEmail());
}
}