package com.bruna.finance.service;

import com.bruna.finance.dto.MonthSummaryResponse;
import com.bruna.finance.dto.TransactionResponse;
import com.bruna.finance.entity.Month;
import com.bruna.finance.entity.MonthStatus;
import com.bruna.finance.entity.User;
import com.bruna.finance.repository.MonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthService {

    private final MonthRepository monthRepository;
    private final TransactionService transactionService;

    public Month createMonth(
        User user,
        Integer month,
        Integer year,
        BigDecimal monthlyIncome,
        BigDecimal savingsGoal) {

        var existingMonth = monthRepository
                .findByUserAndMonthAndYear(user, month, year);

        if (existingMonth.isPresent()) {
            throw new RuntimeException("Este mês já existe.");
        }

        Month newMonth = Month.builder()
                .month(month)
                .year(year)
                .status(MonthStatus.OPEN)
                .monthlyIncome(monthlyIncome)
                .savingsGoal(savingsGoal)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return monthRepository.save(newMonth);
    }

    // ← MÉTODO NOVO
    public Optional<Month> getCurrentMonth(User user) {
        return monthRepository.findFirstByUserAndStatusOrderByYearDescMonthDesc(user, MonthStatus.OPEN);
    }

    public Month closeMonth(Long monthId) {

        Month month = monthRepository.findById(monthId)
                .orElseThrow(() -> new RuntimeException("Mês não encontrado"));

        month.setStatus(MonthStatus.CLOSED);

        return monthRepository.save(month);
    }

    public Month updateSettings(Long monthId, BigDecimal monthlyIncome, BigDecimal savingsGoal) {
    Month month = monthRepository.findById(monthId)
            .orElseThrow(() -> new RuntimeException("Mês não encontrado"));

    month.setMonthlyIncome(monthlyIncome);
    month.setSavingsGoal(savingsGoal);

    return monthRepository.save(month);
}

    public MonthSummaryResponse getSummary(Long monthId) {

        Month month = monthRepository.findById(monthId)
                .orElseThrow(() -> new RuntimeException("Mês não encontrado"));

        var summary = transactionService.getMonthSummary(monthId);

        BigDecimal income = summary.getOrDefault("income", BigDecimal.ZERO);
BigDecimal expenses = summary.getOrDefault("expenses", BigDecimal.ZERO);

BigDecimal monthlyIncomeBase = month.getMonthlyIncome() != null
        ? month.getMonthlyIncome()
        : BigDecimal.ZERO;

income = income.add(monthlyIncomeBase);
BigDecimal balance = income.subtract(expenses);

        BigDecimal savingsGoal = month.getSavingsGoal() != null
                ? month.getSavingsGoal()
                : BigDecimal.ZERO;

        BigDecimal monthlyIncome = month.getMonthlyIncome() != null
                ? month.getMonthlyIncome()
                : BigDecimal.ZERO;

        BigDecimal availableToSpend = monthlyIncome.subtract(savingsGoal);

        int daysInMonth = YearMonth
                .of(month.getYear(), month.getMonth())
                .lengthOfMonth();

        BigDecimal dailyLimit = BigDecimal.ZERO;

        if (daysInMonth > 0) {
            dailyLimit = availableToSpend.divide(
                    BigDecimal.valueOf(daysInMonth),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        LocalDate today = LocalDate.now();

        var transactions = transactionService.getTransactionsByMonth(monthId);

        BigDecimal spentToday = transactions.stream()
                .filter(t -> t.getType().name().equals("EXPENSE"))
                .filter(t -> t.getDate().equals(today))
                .map(TransactionResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingToday = dailyLimit.subtract(spentToday);

        if (remainingToday.compareTo(BigDecimal.ZERO) < 0) {
            remainingToday = BigDecimal.ZERO;
        }

        String warning = null;

        if (spentToday.compareTo(dailyLimit) > 0) {
            warning = "Você ultrapassou o limite diário";
        }

        Integer percentageUsed = 0;

        if (dailyLimit.compareTo(BigDecimal.ZERO) > 0) {
            percentageUsed = spentToday
                    .divide(dailyLimit, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .intValue();
        }

        BigDecimal monthlyRemaining = availableToSpend.subtract(expenses);

        if (monthlyRemaining.compareTo(BigDecimal.ZERO) < 0) {
            monthlyRemaining = BigDecimal.ZERO;
        }

        Integer monthlyProgress = 0;

        if (availableToSpend.compareTo(BigDecimal.ZERO) > 0) {
            monthlyProgress = expenses
                    .divide(availableToSpend, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .intValue();
        }

        var categoryExpenses = transactionService.getExpensesByCategory(monthId);

        String insight = null;

        if (!categoryExpenses.isEmpty()) {
            var topCategory = categoryExpenses.get(0);
            if (topCategory.getPercentage() >= 50) {
                insight = "Você está gastando muito com " + topCategory.getCategory();
            }
        }

        if (monthlyRemaining.compareTo(BigDecimal.ZERO) == 0) {
            insight = "Você já atingiu o limite do mês";
        }

        MonthSummaryResponse response = new MonthSummaryResponse();

        response.setIncome(income);
        response.setExpenses(expenses);
        response.setBalance(balance);
        response.setSavingsGoal(savingsGoal);
        response.setAvailableToSpend(availableToSpend);
        response.setDailyLimit(dailyLimit);
        response.setSpentToday(spentToday);
        response.setRemainingToday(remainingToday);
        response.setWarning(warning);
        response.setPercentageUsed(percentageUsed);
        response.setMonthlyRemaining(monthlyRemaining);
        response.setMonthlyProgress(monthlyProgress);
        response.setInsight(insight);

        return response;
    }
}