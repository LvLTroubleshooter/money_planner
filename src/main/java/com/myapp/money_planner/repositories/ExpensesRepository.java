package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findAllByUser_UserId(Long userId);

    List<Expenses> findAllByUser_UserIdAndAmountBetween(Long userId, Double minAmount, Double maxAmount);

    List<Expenses> findAllByUser_UserIdAndCategory_CategoryId(Long userId, Long categoryId);

    List<Expenses> findAllByUser_UserIdAndExpenseDate(Long userId, LocalDate expenseDate);

    List<Expenses> findAllByUser_UserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    Optional<Expenses> findByUser_UserIdAndExpenseId(Long userId, Long expenseId);
}
