package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.repositories.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    // Create
    public Expenses createExpense(Expenses expense) {
        return expensesRepository.save(expense);
    }

    // Read
    public Optional<Expenses> getExpenseById(Long expenseId) {
        return expensesRepository.findById(expenseId);
    }

    public Optional<Expenses> getExpenseByUserId(Long userId) {
        return expensesRepository.findByUser_UserId(userId);
    }

    public Optional<Expenses> getExpenseByCategoryId(Long categoryId) {
        return expensesRepository.findByCategory_CategoryId(categoryId);
    }

    public Optional<Expenses> getExpenseByDate(LocalDate expenseDate) {
        return expensesRepository.findByExpenseDate(expenseDate);
    }

    // Update
    public Expenses updateExpense(Long expenseId, Expenses expense) {
        if (expensesRepository.existsById(expenseId)) {
            expense.setExpenseId(expenseId); // Ensure we're updating the correct expense
            return expensesRepository.save(expense);
        }
        return null; // Return null if the expense doesn't exist
    }

    // Delete
    public boolean deleteExpense(Long expenseId) {
        if (expensesRepository.existsById(expenseId)) {
            expensesRepository.deleteById(expenseId);
            return true;
        }
        return false; // Return false if the expense doesn't exist
    }
}
