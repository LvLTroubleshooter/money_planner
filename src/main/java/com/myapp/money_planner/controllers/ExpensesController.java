package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;

    @Autowired
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Expenses> createExpense(@RequestBody Expenses expense) {
        Expenses createdExpense = expensesService.createExpense(expense);
        return ResponseEntity.ok(createdExpense);
    }

    // Read by ID
    @GetMapping("/{expenseId}")
    public ResponseEntity<Expenses> getExpenseById(@PathVariable Long expenseId) {
        Optional<Expenses> expense = expensesService.getExpenseById(expenseId);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Expenses> getExpenseByUserId(@PathVariable Long userId) {
        Optional<Expenses> expense = expensesService.getExpenseByUserId(userId);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by Category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Expenses> getExpenseByCategoryId(@PathVariable Long categoryId) {
        Optional<Expenses> expense = expensesService.getExpenseByCategoryId(categoryId);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by Date
    @GetMapping("/date/{expenseDate}")
    public ResponseEntity<Expenses> getExpenseByDate(@PathVariable String expenseDate) {
        LocalDate date = LocalDate.parse(expenseDate);
        Optional<Expenses> expense = expensesService.getExpenseByDate(date);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{expenseId}")
    public ResponseEntity<Expenses> updateExpense(@PathVariable Long expenseId, @RequestBody Expenses expense) {
        Expenses updatedExpense = expensesService.updateExpense(expenseId, expense);
        return updatedExpense != null ? ResponseEntity.ok(updatedExpense) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        boolean deleted = expensesService.deleteExpense(expenseId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
