package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Budgets;
import com.myapp.money_planner.repositories.BudgetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetsService {

    @Autowired
    private BudgetsRepository budgetsRepository;

    // Create Budget
    public Budgets createBudget(Budgets budget) {
        return budgetsRepository.save(budget);
    }

    // Get All Budgets by User
    public List<Budgets> getAllBudgetsByUser(Long userId) {
        return budgetsRepository.findAllByUser_UserId(userId);
    }

    // Get Budget by User and Budget ID
    public Budgets getBudgetByUserAndBudgetId(Long userId, Long budgetId) {
        return budgetsRepository.findByUser_UserIdAndBudgetId(userId, budgetId).orElse(null);
    }

    // Get Budgets by User and Category
    public List<Budgets> getBudgetsByCategory(Long userId, Long categoryId) {
        return budgetsRepository.findAllByUser_UserIdAndCategory_CategoryId(userId, categoryId);
    }

    // Get Budgets by User and Amount Range
    public List<Budgets> getBudgetsByAmountRange(Long userId, Double minAmount, Double maxAmount) {
        return budgetsRepository.findAllByUser_UserIdAndAmountBetween(userId, minAmount, maxAmount);
    }

    // Get Budgets by User and Date Range
    public List<Budgets> getBudgetsByDateRange(Long userId, Date startDate, Date endDate) {
        return budgetsRepository.findAllByUser_UserIdAndStartDateBetween(userId, startDate, endDate);
    }

    // Get Budgets by User and Status
    public List<Budgets> getBudgetsByStatus(Long userId, String status) {
        return budgetsRepository.findAllByUser_UserIdAndBudgetStatus(userId, status);
    }

    // Update Budget
    public Budgets updateBudget(Long userId, Long budgetId, Budgets budget) {
        Optional<Budgets> existingBudget = budgetsRepository.findByUser_UserIdAndBudgetId(userId, budgetId);
        if (existingBudget.isPresent()) {
            budget.setBudgetId(budgetId);
            budget.setUser(existingBudget.get().getUser());
            return budgetsRepository.save(budget);
        }
        return null;
    }

    // Delete Budget
    public boolean deleteBudget(Long userId, Long budgetId) {
        Optional<Budgets> existingBudget = budgetsRepository.findByUser_UserIdAndBudgetId(userId, budgetId);
        if (existingBudget.isPresent()) {
            budgetsRepository.deleteById(budgetId);
            return true;
        }
        return false;
    }
}
