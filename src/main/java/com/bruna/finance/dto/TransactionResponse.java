package com.bruna.finance.dto;

import com.bruna.finance.entity.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class TransactionResponse {

    private Long id;

    private String description;

    private BigDecimal amount;

    private TransactionType type;

    private LocalDate date;

    private String category;

    private String categoryName;

    public String getCategoryName() {
    return categoryName;
}

public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
}

}