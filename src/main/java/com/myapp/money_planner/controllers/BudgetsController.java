package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Budgets;
import com.myapp.money_planner.services.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetsController {

    private final BudgetsService budgetsService;

    @Autowired
    public BudgetsController(BudgetsService budgetsService) {
        this.budgetsService = budgetsService;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Budgets> createBudget(@RequestBody Budgets budget) {
        Budgets createdBudget = budgetsService.createBudget(budget);
        return ResponseEntity.ok(createdBudget);
    }

    // Read by ID
    @GetMapping("/{budgetId}")
    public ResponseEntity<Budgets> getBudgetById(@PathVariable Long budgetId) {
        Optional<Budgets> budget = budgetsService.getBudgetById(budgetId);
        return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Budgets> getBudgetByUserId(@PathVariable Long userId) {
        Optional<Budgets> budget = budgetsService.getBudgetByUserId(userId);
        return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by Category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Budgets> getBudgetByCategoryId(@PathVariable Long categoryId) {
        Optional<Budgets> budget = budgetsService.getBudgetByCategoryId(categoryId);
        return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{budgetId}")
    public ResponseEntity<Budgets> updateBudget(@PathVariable Long budgetId, @RequestBody Budgets budget) {
        Budgets updatedBudget = budgetsService.updateBudget(budgetId, budget);
        return updatedBudget != null ? ResponseEntity.ok(updatedBudget) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long budgetId) {
        boolean deleted = budgetsService.deleteBudget(budgetId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
