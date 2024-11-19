package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Expenses;
import com.myapp.money_planner.repositories.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    public Expenses createExpense(Expenses expense) {
        expense.setCreatedAt(null);
        return expensesRepository.save(expense);
    }

    public List<Expenses> getAllExpensesByUser(Long userId) {
        return expensesRepository.findAllByUser_UserId(userId);
    }

    public List<Expenses> getExpensesByAmountRange(Long userId, Double minAmount, Double maxAmount) {
        return expensesRepository.findAllByUser_UserIdAndAmountBetween(userId, minAmount, maxAmount);
    }

    public List<Expenses> getExpensesByCategory(Long userId, Long categoryId) {
        return expensesRepository.findAllByUser_UserIdAndCategory_CategoryId(userId, categoryId);
    }

    public List<Expenses> getExpensesByDate(Long userId, LocalDate expenseDate) {
        return expensesRepository.findAllByUser_UserIdAndExpenseDate(userId, expenseDate);
    }

    public List<Expenses> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expensesRepository.findAllByUser_UserIdAndExpenseDateBetween(userId, startDate, endDate);
    }

    public Expenses updateExpense(Long userId, Long expenseId, Expenses expense) {
        Optional<Expenses> existingExpense = expensesRepository.findByUser_UserIdAndExpenseId(userId, expenseId);
        if (existingExpense.isPresent()) {
            expense.setExpenseId(expenseId);
            expense.setUser(existingExpense.get().getUser());
            return expensesRepository.save(expense);
        }
        return null;
    }

    public boolean deleteExpense(Long userId, Long expenseId) {
        Optional<Expenses> existingExpense = expensesRepository.findByUser_UserIdAndExpenseId(userId, expenseId);
        if (existingExpense.isPresent()) {
            expensesRepository.deleteById(expenseId);
            return true;
        }
        return false;
    }
}
