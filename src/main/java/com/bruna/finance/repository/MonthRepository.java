package com.bruna.finance.repository;

import com.bruna.finance.entity.Month;
import com.bruna.finance.entity.MonthStatus;
import com.bruna.finance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthRepository extends JpaRepository<Month, Long> {

    Optional<Month> findByUserAndMonthAndYear(User user, Integer month, Integer year);
    Optional<Month> findFirstByUserAndStatusOrderByYearDescMonthDesc(User user, MonthStatus status);
}