package com.bruna.finance.dto;

import com.bruna.finance.entity.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    private String name;

    private TransactionType type;

}