package com.bruna.finance.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor

public class CategoryExpenseResponse {

    private String category;
    private BigDecimal total;
    private Integer percentage;

    public CategoryExpenseResponse(String category, BigDecimal total) {
        this.category = category;
        this.total = total;
    }
}