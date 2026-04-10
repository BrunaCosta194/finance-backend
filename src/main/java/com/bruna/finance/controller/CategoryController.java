package com.bruna.finance.controller;

import com.bruna.finance.dto.CategoryResponse;
import com.bruna.finance.dto.CreateCategoryRequest;
import com.bruna.finance.entity.Category;
import com.bruna.finance.entity.TransactionType;
import com.bruna.finance.repository.UserRepository;
import com.bruna.finance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.bruna.finance.entity.User;


import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserRepository userRepository;

   @GetMapping
public List<CategoryResponse> getCategories() {
    return categoryService.getCategories(null);
}
@PostMapping
public CategoryResponse createCategory(
        @RequestBody CreateCategoryRequest request,
        @AuthenticationPrincipal User user
) {

    return categoryService.createCategory(
            user,
            request.getName(),
            request.getType()
    );
}
@GetMapping("/type/{type}")
public List<CategoryResponse> getCategoriesByType(@PathVariable TransactionType type) {
    return categoryService.getCategoriesByType(type);
}

}