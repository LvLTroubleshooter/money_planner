package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.repositories.CategoriesRepository;
import com.myapp.money_planner.repositories.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Expenses createExpense(Expenses expense) {
        logger.info("Creating expense: {}", expense);
        try {
            // Validate category
            if (expense.getCategory() == null || expense.getCategory().getCategoryId() == null) {
                logger.error("Category information is missing");
                throw new RuntimeException("Category information is required");
            }

            // Validate user
            if (expense.getUser() == null || expense.getUser().getUserId() == null) {
                logger.error("User information is missing");
                throw new RuntimeException("User information is required");
            }

            // Fetch and validate category
            Categories category = categoriesRepository.findById(expense.getCategory().getCategoryId())
                    .orElseThrow(() -> {
                        logger.error("Category not found with ID: {}", expense.getCategory().getCategoryId());
                        return new RuntimeException("Category not found");
                    });

            expense.setCategory(category);
            expense.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            Expenses savedExpense = expenseRepository.save(expense);
            logger.info("Successfully created expense with ID: {}", savedExpense.getExpenseId());
            return savedExpense;
        } catch (Exception e) {
            logger.error("Error creating expense: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating expense: " + e.getMessage(), e);
        }
    }

    public List<Expenses> getExpensesByUser(Long userId) {
        List<Expenses> expenses = expenseRepository.findByUser_UserId(userId);
        expenses.forEach(expense -> {
            Categories category = categoriesRepository.findById(expense.getCategory().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            expense.setCategory(category);
        });
        return expenses;
    }

    public List<Expenses> getAllExpensesByUser(Long userId) {
        return expenseRepository.findByUser_UserId(userId);
    }

    public List<Expenses> getExpensesByAmountRange(Long userId, Double minAmount, Double maxAmount) {
        return expenseRepository.findByUser_UserIdAndAmountBetween(userId, minAmount, maxAmount);
    }

    public List<Expenses> getExpensesByCategory(Long userId, Long categoryId) {
        return expenseRepository.findByUser_UserIdAndCategory_CategoryId(userId, categoryId);
    }

    public List<Expenses> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUser_UserIdAndExpenseDateBetween(userId, startDate, endDate);
    }

    public Expenses updateExpense(Long userId, Long expenseId, Expenses expense) {
        Optional<Expenses> existingExpense = expenseRepository.findByExpenseIdAndUser_UserId(expenseId, userId);
        if (existingExpense.isPresent()) {
            Expenses expenseToUpdate = existingExpense.get();
            expenseToUpdate.setAmount(expense.getAmount());
            expenseToUpdate.setExpenseDate(expense.getExpenseDate());
            expenseToUpdate.setCategory(expense.getCategory());
            return expenseRepository.save(expenseToUpdate);
        }
        return null;
    }

    public boolean deleteExpense(Long userId, Long expenseId) {
        Optional<Expenses> expense = expenseRepository.findByExpenseIdAndUser_UserId(expenseId, userId);
        if (expense.isPresent()) {
            expenseRepository.delete(expense.get());
            return true;
        }
        return false;
    }

    // ... rest of the service methods
}