package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Expenses;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    @NonNull
    Optional<Expenses> findByUser_UserId(Long userId);

    @NonNull
    Optional<Expenses> findByCategory_CategoryId(@NonNull Long categoryId); // category_id_FK is NOT NULL

    @NonNull
    Optional<Expenses> findByExpenseDate(@NonNull LocalDate expenseDate); // expense_date is NOT NULL

    @NonNull
    Optional<Expenses> findById(@NonNull Long expenseId);
}