package com.bruna.finance.service;

import com.bruna.finance.entity.Month;
import com.bruna.finance.entity.MonthStatus;
import com.bruna.finance.entity.Transaction;
import com.bruna.finance.entity.TransactionType;
import com.bruna.finance.entity.User;
import com.bruna.finance.repository.CategoryRepository;
import com.bruna.finance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.bruna.finance.dto.CategoryExpenseResponse;
import com.bruna.finance.dto.TransactionResponse;
import com.bruna.finance.entity.Category;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public Transaction createTransaction(
        User user,
        Month month,
        Long categoryId,
        String description,
        BigDecimal amount,
        TransactionType type,
        LocalDate date
) {

    if (month.getStatus() == MonthStatus.CLOSED) {
        throw new RuntimeException("Este mês está fechado e não pode receber novas transações.");
    }

    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            
    if (!category.getType().equals(type)) {
         throw new RuntimeException("Tipo da categoria não corresponde ao tipo da transação.");
}

    Transaction transaction = Transaction.builder()
            .description(description)
            .amount(amount)
            .type(type)
            .date(date)
            .user(user)
            .month(month)
            .category(category)
            .createdAt(LocalDateTime.now())
            .build();

    return transactionRepository.save(transaction);
}
public Map<String, BigDecimal> getMonthSummary(Long monthId) {

    List<Transaction> transactions =
        transactionRepository.findByMonthIdOrderByDateDesc(monthId);
    BigDecimal income = BigDecimal.ZERO;
    BigDecimal expenses = BigDecimal.ZERO;

    for (Transaction transaction : transactions) {

        if (transaction.getType() == TransactionType.INCOME) {
            income = income.add(transaction.getAmount());
        } else {
            expenses = expenses.add(transaction.getAmount());
        }

    }

    BigDecimal balance = income.subtract(expenses);

    Map<String, BigDecimal> summary = new HashMap<>();

    summary.put("income", income);
    summary.put("expenses", expenses);
    summary.put("balance", balance);

    return summary;
}
public List<CategoryExpenseResponse> getExpensesByCategory(Long monthId) {

    List<CategoryExpenseResponse> expenses =
            transactionRepository.sumExpensesByCategory(monthId);

    BigDecimal totalExpenses = expenses.stream()
            .map(CategoryExpenseResponse::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return expenses.stream()
            .map(item -> {

                Integer percentage = 0;

                if (totalExpenses.compareTo(BigDecimal.ZERO) > 0) {
                    percentage = item.getTotal()
                            .divide(totalExpenses, 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .intValue();
                }

                item.setPercentage(percentage);
                return item;
            })
            .toList();
}

public List<TransactionResponse> getTransactionsByMonth(Long monthId) {

    return transactionRepository
            .findByMonthIdOrderByDateDesc(monthId)
            .stream()
            .map(this::toResponse)
            .toList();
}
private TransactionResponse toResponse(Transaction transaction) {
    return TransactionResponse.builder()
            .id(transaction.getId())
            .description(transaction.getDescription())
            .amount(transaction.getAmount())
            .type(transaction.getType())
            .date(transaction.getDate())
            .category(
                transaction.getCategory() != null
                        ? transaction.getCategory().getName()
                        : null
            )
            .build();
}
}