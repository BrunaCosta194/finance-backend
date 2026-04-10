package com.bruna.finance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bruna.finance.service.CategoryService;

@SpringBootApplication
public class FinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

@Bean
CommandLineRunner init(CategoryService categoryService) {
    return args -> {
        categoryService.createDefaultCategories();
    };
}	

}
