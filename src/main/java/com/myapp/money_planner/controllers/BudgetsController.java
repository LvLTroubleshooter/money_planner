package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Budgets;
import com.myapp.money_planner.services.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetsController {

    private final BudgetsService budgetsService;

    @Autowired
    public BudgetsController(BudgetsService budgetsService) {
        this.budgetsService = budgetsService;
    }

    // Create
    @PostMapping("/user/{userId}/category/{categoryId}/create")
    public ResponseEntity<Budgets> createBudget(@PathVariable Long userId, @PathVariable Long categoryId, @RequestBody Budgets budget) {
        budget.getUser().setUserId(userId);
        budget.getCategory().setCategoryId(categoryId);
        Budgets createdBudget = budgetsService.createBudget(budget);
        return ResponseEntity.ok(createdBudget);
    }

    // Get All Budgets by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budgets>> getAllBudgetsByUser(@PathVariable Long userId) {
        List<Budgets> budgets = budgetsService.getAllBudgetsByUser(userId);
        return budgets.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(budgets);
    }

    // Get Budget by User ID and Budget ID
    @GetMapping("/user/{userId}/budget/{budgetId}")
    public ResponseEntity<Budgets> getBudgetByUserAndBudgetId(@PathVariable Long userId, @PathVariable Long budgetId) {
        Budgets budget = budgetsService.getBudgetByUserAndBudgetId(userId, budgetId);
        return budget != null ? ResponseEntity.ok(budget) : ResponseEntity.notFound().build();
    }

    // Get Budgets by User and Category
    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<Budgets>> getBudgetsByCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        List<Budgets> budgets = budgetsService.getBudgetsByCategory(userId, categoryId);
        return budgets.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(budgets);
    }

    // Get Budgets by User and Amount Range
    @GetMapping("/user/{userId}/amount-range")
    public ResponseEntity<List<Budgets>> getBudgetsByAmountRange(@PathVariable Long userId,
                                                                 @RequestParam Double minAmount,
                                                                 @RequestParam Double maxAmount) {
        List<Budgets> budgets = budgetsService.getBudgetsByAmountRange(userId, minAmount, maxAmount);
        return budgets.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(budgets);
    }

    // Get Budgets by User and Date Range
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Budgets>> getBudgetsByDateRange(@PathVariable Long userId,
                                                               @RequestParam Date startDate,
                                                               @RequestParam Date endDate) {
        List<Budgets> budgets = budgetsService.getBudgetsByDateRange(userId, startDate, endDate);
        return budgets.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(budgets);
    }

    // Get Budgets by User and Status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Budgets>> getBudgetsByStatus(@PathVariable Long userId, @PathVariable String status) {
        List<Budgets> budgets = budgetsService.getBudgetsByStatus(userId, status);
        return budgets.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(budgets);
    }

    // Update Budget
    @PutMapping("/user/{userId}/budget/{budgetId}")
    public ResponseEntity<Budgets> updateBudget(@PathVariable Long userId, @PathVariable Long budgetId, @RequestBody Budgets budget) {
        Budgets updatedBudget = budgetsService.updateBudget(userId, budgetId, budget);
        return updatedBudget != null ? ResponseEntity.ok(updatedBudget) : ResponseEntity.notFound().build();
    }

    // Delete Budget
    @DeleteMapping("/user/{userId}/budget/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long userId, @PathVariable Long budgetId) {
        boolean deleted = budgetsService.deleteBudget(userId, budgetId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
