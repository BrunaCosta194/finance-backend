package com.bruna.finance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "months")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private MonthStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(precision = 10, scale = 2)
    private BigDecimal monthlyIncome;

    @Column(precision = 10, scale = 2)
    private BigDecimal savingsGoal;

    private LocalDateTime createdAt;

    public BigDecimal getMonthlyIncome() {
    return monthlyIncome;
}

public void setMonthlyIncome(BigDecimal monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
}

public BigDecimal getSavingsGoal() {
    return savingsGoal;
}

public void setSavingsGoal(BigDecimal savingsGoal) {
    this.savingsGoal = savingsGoal;
}
}
