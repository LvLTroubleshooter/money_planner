package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpensesController {

    private static final Logger logger = LoggerFactory.getLogger(ExpensesController.class);

    private final ExpenseService expenseService;

    @Autowired
    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody Expenses expense) {
        try {
            logger.info("Received request to create expense: {}", expense);
            logger.info("Category ID: {}", expense.getCategory().getCategoryId());
            logger.info("User ID: {}", expense.getUser().getUserId());

            // Validate request
            if (expense.getUser() == null || expense.getUser().getUserId() == null) {
                logger.error("User information is missing in the request");
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "User information is required");
                            }
                        });
            }

            if (expense.getCategory() == null || expense.getCategory().getCategoryId() == null) {
                logger.error("Category information is missing in the request");
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "Category information is required");
                            }
                        });
            }

            Expenses newExpense = expenseService.createExpense(expense);
            logger.info("Created expense: {}", newExpense);
            return ResponseEntity.ok(newExpense);
        } catch (Exception e) {
            logger.error("Error creating expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {
                        {
                            put("error", "Error creating expense: " + e.getMessage());
                        }
                    });
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expenses>> getUserExpenses(@PathVariable Long userId) {
        try {
            List<Expenses> expenses = expenseService.getAllExpensesByUser(userId);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user/{userId}/amount-range")
    public ResponseEntity<List<Expenses>> getExpensesByAmountRange(
            @PathVariable Long userId, @RequestParam Double minAmount, @RequestParam Double maxAmount) {
        List<Expenses> expenses = expenseService.getExpensesByAmountRange(userId, minAmount, maxAmount);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<Expenses>> getExpensesByCategory(
            @PathVariable Long userId, @PathVariable Long categoryId) {
        List<Expenses> expenses = expenseService.getExpensesByCategory(userId, categoryId);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Expenses>> getExpensesByDateRange(
            @PathVariable Long userId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Expenses> expenses = expenseService.getExpensesByDateRange(userId, start, end);
        return expenses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(expenses);
    }

    @PutMapping("/user/{userId}/expense/{expenseId}")
    public ResponseEntity<Expenses> updateExpense(
            @PathVariable Long userId, @PathVariable Long expenseId, @RequestBody Expenses expense) {
        Expenses updatedExpense = expenseService.updateExpense(userId, expenseId, expense);
        return updatedExpense != null ? ResponseEntity.ok(updatedExpense) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}/expense/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        boolean deleted = expenseService.deleteExpense(userId, expenseId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
