package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Budgets;
import com.myapp.money_planner.repositories.BudgetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetsService {

    @Autowired
    private BudgetsRepository budgetsRepository;

    // Create
    public Budgets createBudget(Budgets budget) {
        return budgetsRepository.save(budget);
    }

    // Read
    public Optional<Budgets> getBudgetById(Long budgetId) {
        return budgetsRepository.findById(budgetId);
    }

    public Optional<Budgets> getBudgetByUserId(Long userId) {
        return budgetsRepository.findByUser_UserId(userId);
    }

    public Optional<Budgets> getBudgetByCategoryId(Long categoryId) {
        return budgetsRepository.findByCategory_CategoryId(categoryId);
    }

    // Update
    public Budgets updateBudget(Long budgetId, Budgets budget) {
        if (budgetsRepository.existsById(budgetId)) {
            budget.setBudgetId(budgetId); // Ensure we're updating the correct budget
            return budgetsRepository.save(budget);
        }
        return null; // Return null if the budget doesn't exist
    }

    // Delete
    public boolean deleteBudget(Long budgetId) {
        if (budgetsRepository.existsById(budgetId)) {
            budgetsRepository.deleteById(budgetId);
            return true;
        }
        return false; // Return false if the budget doesn't exist
    }
}
