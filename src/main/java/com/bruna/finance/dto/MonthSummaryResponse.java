package com.bruna.finance.dto;

import java.math.BigDecimal;

public class MonthSummaryResponse {

    private BigDecimal income;
    private BigDecimal expenses;
    private BigDecimal balance;

    private BigDecimal savingsGoal;
    private BigDecimal availableToSpend;
    private BigDecimal dailyLimit;
    private BigDecimal spentToday;
    private BigDecimal remainingToday;
    private String warning;
    private Integer percentageUsed;
    private BigDecimal monthlyRemaining;
    private Integer monthlyProgress;
    private String insight;

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSavingsGoal() {
        return savingsGoal;
    }

    public void setSavingsGoal(BigDecimal savingsGoal) {
        this.savingsGoal = savingsGoal;
    }

    public BigDecimal getAvailableToSpend() {
        return availableToSpend;
    }

    public void setAvailableToSpend(BigDecimal availableToSpend) {
        this.availableToSpend = availableToSpend;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;

    }
    public BigDecimal getSpentToday() {
    return spentToday;
    }

    public void setSpentToday(BigDecimal spentToday) {
    this.spentToday = spentToday;
    }

    public BigDecimal getRemainingToday() {
    return remainingToday;
    }

    public void setRemainingToday(BigDecimal remainingToday) {
    this.remainingToday = remainingToday;
    }

    public String getWarning() {
    return warning;
    }

    public void setWarning(String warning) {
    this.warning = warning;
    }
    public Integer getPercentageUsed() {
    return percentageUsed;
    }

    public void setPercentageUsed(Integer percentageUsed) {
    this.percentageUsed = percentageUsed;
    }
    public BigDecimal getMonthlyRemaining() {
    return monthlyRemaining;
    }

    public void setMonthlyRemaining(BigDecimal monthlyRemaining) {
    this.monthlyRemaining = monthlyRemaining;
    }
    public Integer getMonthlyProgress() {
    return monthlyProgress;
    }

    public void setMonthlyProgress(Integer monthlyProgress) {
    this.monthlyProgress = monthlyProgress;
    }
    public String getInsight() {
    return insight;
    }

    public void setInsight(String insight) {
    this.insight = insight;
    }
}