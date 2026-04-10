package com.bruna.finance.service;

import com.bruna.finance.dto.CategoryResponse;
import com.bruna.finance.entity.Category;
import com.bruna.finance.entity.TransactionType;
import com.bruna.finance.entity.User;
import com.bruna.finance.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories(User user) {

    List<Category> categories;

    if (user == null) {
        categories = categoryRepository.findBySystemTrue();
    } else {
        categories = categoryRepository.findBySystemTrueOrUser(user);
    }

    return categories.stream()
            .map(category -> CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .type(category.getType())
                    .build())
            .toList();
}
public List<CategoryResponse> getCategoriesByType(TransactionType type) {

    List<Category> categories = categoryRepository.findByType(type);

    return categories.stream()
            .map(category -> CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .type(category.getType())
                    .build())
            .toList();
}
    public CategoryResponse createCategory(User user, String name, TransactionType type) {

    Category category = new Category();

    category.setName(name);
    category.setType(type);
    category.setSystem(false);
    category.setUser(user);

    Category saved = categoryRepository.save(category);

    return CategoryResponse.builder()
            .id(saved.getId())
            .name(saved.getName())
            .type(saved.getType())
            .build();
}
public void createDefaultCategories() {

    if (!categoryRepository.findBySystemTrue().isEmpty()) {
        return;
    }

    categoryRepository.save(Category.builder()
            .name("Alimentação")
            .type(TransactionType.EXPENSE)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Moradia")
            .type(TransactionType.EXPENSE)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Transporte")
            .type(TransactionType.EXPENSE)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Lazer")
            .type(TransactionType.EXPENSE)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Saúde")
            .type(TransactionType.EXPENSE)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Educação")
            .type(TransactionType.EXPENSE)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Salário")
            .type(TransactionType.INCOME)
            .system(true)
            .build());

    categoryRepository.save(Category.builder()
            .name("Freelance")
            .type(TransactionType.INCOME)
            .system(true)
            .build());
}
}