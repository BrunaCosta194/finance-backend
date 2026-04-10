package com.bruna.finance.config;

import com.bruna.finance.entity.Category;
import com.bruna.finance.entity.TransactionType;
import com.bruna.finance.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if (categoryRepository.count() == 0) {

            categoryRepository.save(
                    Category.builder()
                            .name("Alimentação")
                            .type(TransactionType.EXPENSE)
                            .system(true)
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .name("Transporte")
                            .type(TransactionType.EXPENSE)
                            .system(true)
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .name("Moradia")
                            .type(TransactionType.EXPENSE)
                            .system(true)
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .name("Saúde")
                            .type(TransactionType.EXPENSE)
                            .system(true)
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .name("Lazer")
                            .type(TransactionType.EXPENSE)
                            .system(true)
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .name("Salário")
                            .type(TransactionType.INCOME)
                            .system(true)
                            .build()
            );

            categoryRepository.save(
                    Category.builder()
                            .name("Freelance")
                            .type(TransactionType.INCOME)
                            .system(true)
                            .build()
            );

        }

    }

}