package com.bruna.finance.repository;

import com.bruna.finance.entity.Transaction;
import com.bruna.finance.dto.CategoryExpenseResponse;
import com.bruna.finance.entity.Month;
import com.bruna.finance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByMonth(Month month);

    List<Transaction> findByMonthIdOrderByDateDesc(Long monthId);

    @Query("""
SELECT new com.bruna.finance.dto.CategoryExpenseResponse(
    t.category.name,
    SUM(t.amount)
)
FROM Transaction t
WHERE t.month.id = :monthId
AND t.type = com.bruna.finance.entity.TransactionType.EXPENSE
GROUP BY t.category.name
""")
List<CategoryExpenseResponse> sumExpensesByCategory(Long monthId);

}

