package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findByUserUserId(Long userId);

    List<Expenses> findByUserUserIdAndAmountBetween(Long userId, Double minAmount, Double maxAmount);

    List<Expenses> findByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);

    List<Expenses> findByUserUserIdAndExpenseDate(Long userId, LocalDate expenseDate);

    List<Expenses> findByUserUserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    Optional<Expenses> findByExpenseIdAndUserUserId(Long expenseId, Long userId);

    List<Expenses> findByCategoryCategoryId(Long categoryId);
}
