package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.repositories.CategoriesRepository;
import com.myapp.money_planner.repositories.ExpensesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesService {
    private static final Logger logger = LoggerFactory.getLogger(ExpensesService.class);

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Expenses> getExpensesByUser(Long userId) {
        return expensesRepository.findByUserUserId(userId);
    }

    public List<Expenses> getAllExpensesByUser(Long userId) {
        return expensesRepository.findByUserUserId(userId);
    }

    public List<Expenses> getExpensesByAmountRange(Long userId, Double minAmount, Double maxAmount) {
        return expensesRepository.findByUserUserIdAndAmountBetween(userId, minAmount, maxAmount);
    }

    public List<Expenses> getExpensesByCategory(Long userId, Long categoryId) {
        return expensesRepository.findByUserUserIdAndCategoryCategoryId(userId, categoryId);
    }

    public List<Expenses> getExpensesByDate(Long userId, LocalDate date) {
        return expensesRepository.findByUserUserIdAndExpenseDate(userId, date);
    }

    public List<Expenses> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expensesRepository.findByUserUserIdAndExpenseDateBetween(userId, startDate, endDate);
    }

    public Optional<Expenses> getExpenseById(Long userId, Long expenseId) {
        return expensesRepository.findByExpenseIdAndUserUserId(expenseId, userId);
    }

    public Expenses updateExpense(Long userId, Long expenseId, Expenses expense) {
        Optional<Expenses> existingExpense = expensesRepository.findByExpenseIdAndUserUserId(expenseId, userId);
        if (existingExpense.isPresent()) {
            Expenses expenseToUpdate = existingExpense.get();
            expenseToUpdate.setAmount(expense.getAmount());
            expenseToUpdate.setExpenseDate(expense.getExpenseDate());
            expenseToUpdate.setCategory(expense.getCategory());
            return expensesRepository.save(expenseToUpdate);
        }
        return null;
    }

    public boolean deleteExpense(Long userId, Long expenseId) {
        Optional<Expenses> expense = expensesRepository.findByExpenseIdAndUserUserId(expenseId, userId);
        if (expense.isPresent()) {
            expensesRepository.delete(expense.get());
            return true;
        }
        return false;
    }

    public List<Expenses> updateExpensesCategory(Long categoryId, Categories updatedCategory) {
        List<Expenses> expenses = expensesRepository.findByCategoryCategoryId(categoryId);
        for (Expenses expense : expenses) {
            expense.setCategory(updatedCategory);
        }
        return expensesRepository.saveAll(expenses);
    }

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

            Expenses savedExpense = expensesRepository.save(expense);
            logger.info("Successfully created expense with ID: {}", savedExpense.getExpenseId());
            return savedExpense;
        } catch (Exception e) {
            logger.error("Error creating expense: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating expense: " + e.getMessage(), e);
        }
    }
}
