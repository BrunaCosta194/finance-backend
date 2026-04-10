package com.bruna.finance.controller;

import com.bruna.finance.entity.Month;
import com.bruna.finance.entity.Transaction;
import com.bruna.finance.entity.TransactionType;
import com.bruna.finance.entity.User;
import com.bruna.finance.repository.MonthRepository;
import com.bruna.finance.repository.UserRepository;
import com.bruna.finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final MonthRepository monthRepository;

    @PostMapping
    public Transaction createTransaction(
            @RequestParam Long monthId,
            @RequestParam Long categoryId,
            @RequestParam String description,
            @RequestParam BigDecimal amount,
            @RequestParam TransactionType type,
            @RequestParam String date,
            Authentication authentication
    ) {

        System.out.println("CHEGOU NO ENDPOINT /transactions");
        System.out.println("monthId: " + monthId);
        System.out.println("categoryId: " + categoryId);
        System.out.println("description: " + description);
        System.out.println("amount: " + amount);
        System.out.println("type: " + type);
        System.out.println("date: " + date);
        System.out.println("authentication: " + authentication);

        User user;

        if (authentication != null) {
            String email = authentication.getName();

            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } else {
            user = userRepository.findAll().get(0);
        }

        Month month = monthRepository.findById(monthId)
                .orElseThrow(() -> new RuntimeException("Mês não encontrado"));

        return transactionService.createTransaction(
                user,
                month,
                categoryId,
                description,
                amount,
                type,
                LocalDate.parse(date)
        );
    }
}