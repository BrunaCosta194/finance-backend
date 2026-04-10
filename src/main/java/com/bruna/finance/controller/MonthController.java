package com.bruna.finance.controller;

import com.bruna.finance.dto.CategoryExpenseResponse;
import com.bruna.finance.dto.MonthSummaryResponse;
import com.bruna.finance.dto.TransactionResponse;
import com.bruna.finance.entity.Month;
import com.bruna.finance.entity.User;
import com.bruna.finance.repository.UserRepository;
import com.bruna.finance.service.MonthService;
import com.bruna.finance.service.TransactionService;

import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/months")
@RequiredArgsConstructor
public class MonthController {

    private final MonthService monthService;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @PatchMapping("/{id}/settings")
public ResponseEntity<Month> updateSettings(
        @PathVariable Long id,
        @RequestParam BigDecimal monthlyIncome,
        @RequestParam BigDecimal savingsGoal) {

    return ResponseEntity.ok(monthService.updateSettings(id, monthlyIncome, savingsGoal));
}

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentMonth(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return monthService.getCurrentMonth(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Month createMonth(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam BigDecimal monthlyIncome,
            @RequestParam BigDecimal savingsGoal,
            Authentication authentication) {

        User user;

        if (authentication != null) {
            String email = authentication.getName();
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } else {
            user = userRepository.findAll().get(0);
        }

        return monthService.createMonth(user, month, year, monthlyIncome, savingsGoal);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<MonthSummaryResponse> getMonthSummary(@PathVariable Long id) {
        return ResponseEntity.ok(monthService.getSummary(id));
    }

    @GetMapping("/{id}/expenses-by-category")
    public List<CategoryExpenseResponse> getExpensesByCategory(@PathVariable Long id) {
        return transactionService.getExpensesByCategory(id);
    }

    @GetMapping("/{monthId}/transactions")
    public List<TransactionResponse> getTransactionsByMonth(@PathVariable Long monthId) {
        return transactionService.getTransactionsByMonth(monthId);
    }
}