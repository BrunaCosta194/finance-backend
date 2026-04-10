package com.bruna.finance.repository;

import com.bruna.finance.entity.Category;
import com.bruna.finance.entity.User;
import com.bruna.finance.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);

    List<Category> findBySystemTrueOrUser(User user);

    List<Category> findBySystemTrue();

    List<Category> findByType(TransactionType type);

}