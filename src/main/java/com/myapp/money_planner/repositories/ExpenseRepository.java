package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findByUser_UserId(Long userId);

    Optional<Expenses> findByExpenseIdAndUser_UserId(Long expenseId, Long userId);

    List<Expenses> findByUser_UserIdAndAmountBetween(Long userId, Double minAmount, Double maxAmount);

    List<Expenses> findByUser_UserIdAndCategory_CategoryId(Long userId, Long categoryId);

    List<Expenses> findByUser_UserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}