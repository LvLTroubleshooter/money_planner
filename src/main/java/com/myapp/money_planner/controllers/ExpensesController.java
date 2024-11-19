package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;

    @Autowired
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @PostMapping("/user/{userId}/create")
    public ResponseEntity<Expenses> createExpense(@PathVariable Long userId, @RequestBody Expenses expense) {
        expense.getUser().setUserId(userId);
        Expenses createdExpense = expensesService.createExpense(expense);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expenses>> getAllExpensesByUser(@PathVariable Long userId) {
        List<Expenses> expenses = expensesService.getAllExpensesByUser(userId);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/amount-range")
    public ResponseEntity<List<Expenses>> getExpensesByAmountRange(
            @PathVariable Long userId, @RequestParam Double minAmount, @RequestParam Double maxAmount) {
        List<Expenses> expenses = expensesService.getExpensesByAmountRange(userId, minAmount, maxAmount);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<Expenses>> getExpensesByCategory(
            @PathVariable Long userId, @PathVariable Long categoryId) {
        List<Expenses> expenses = expensesService.getExpensesByCategory(userId, categoryId);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Expenses>> getExpensesByDateRange(
            @PathVariable Long userId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Expenses> expenses = expensesService.getExpensesByDateRange(userId, start, end);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @PutMapping("/user/{userId}/expense/{expenseId}")
    public ResponseEntity<Expenses> updateExpense(
            @PathVariable Long userId, @PathVariable Long expenseId, @RequestBody Expenses expense) {
        Expenses updatedExpense = expensesService.updateExpense(userId, expenseId, expense);
        return updatedExpense != null ? ResponseEntity.ok(updatedExpense) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}/expense/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        boolean deleted = expensesService.deleteExpense(userId, expenseId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
