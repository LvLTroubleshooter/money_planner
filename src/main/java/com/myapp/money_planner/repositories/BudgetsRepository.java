package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetsRepository extends JpaRepository<Budgets, Long> {

    List<Budgets> findAllByUser_UserId(Long userId);

    Optional<Budgets> findByUser_UserIdAndBudgetId(Long userId, Long budgetId);

    List<Budgets> findAllByUser_UserIdAndCategory_CategoryId(Long userId, Long categoryId);

    List<Budgets> findAllByUser_UserIdAndAmountBetween(Long userId, Double minAmount, Double maxAmount);

    List<Budgets> findAllByUser_UserIdAndStartDateBetween(Long userId, Date startDate, Date endDate);

    List<Budgets> findAllByUser_UserIdAndBudgetStatus(Long userId, String status);
}
