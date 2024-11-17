package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Budgets;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetsRepository extends JpaRepository<Budgets, Long> {
    @NonNull
    Optional<Budgets> findByUser_UserId(@NonNull Long userId); // user_id_FK is NOT NULL

    @NonNull
    Optional<Budgets> findByCategory_CategoryId(Long categoryId);

    @NonNull
    Optional<Budgets> findById(@NonNull Long budgetId);
}
