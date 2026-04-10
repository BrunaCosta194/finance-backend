package com.bruna.finance.dto;

import com.bruna.finance.entity.TransactionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private TransactionType type;
    



}
