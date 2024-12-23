package com.myapp.money_planner.controllers;

import com.myapp.money_planner.dto.TrendData;
import com.myapp.money_planner.dto.TrendData.DateAmountPair;
import com.myapp.money_planner.models.Transactions;
import com.myapp.money_planner.repositories.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionsController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);
    private final TransactionsRepository transactionsRepository;

    public TransactionsController(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<Transactions>> getRecentTransactions(@PathVariable Long userId) {
        try {
            logger.info("Fetching recent transactions for user: {}", userId);
            if (userId == null) {
                logger.error("User ID is null");
                return ResponseEntity.ok(new ArrayList<>());
            }

            List<Transactions> transactions = transactionsRepository.findTop4ByUserIdOrderByCreatedAtDesc(userId);
            logger.info("Found {} recent transactions for user {}", transactions.size(), userId);

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error fetching recent transactions for user {}: {}", userId, e.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<Transactions>> getAllTransactions(@PathVariable Long userId) {
        try {
            logger.info("Fetching all transactions for user: {}", userId);
            List<Transactions> transactions = transactionsRepository.findByUserIdOrderByCreatedAtDesc(userId);
            logger.info("Found {} transactions for user {}", transactions.size(), userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error fetching all transactions for user {}: {}", userId, e.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/user/{userId}/trends")
    public ResponseEntity<TrendData> getTransactionTrends(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "week") String period) {
        try {
            logger.info("Fetching transaction trends for user: {} with period: {}", userId, period);

            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = switch (period.toLowerCase()) {
                case "month" -> endDate.minusMonths(1);
                case "year" -> endDate.minusYears(1);
                case "week" -> endDate.minusWeeks(1);
                default -> endDate.minusWeeks(1);
            };

            List<Transactions> transactions = transactionsRepository
                    .findByUserIdAndCreatedAtBetweenOrderByCreatedAtAsc(userId, startDate, endDate);

            Map<String, Double> incomeByDate = new TreeMap<>();
            Map<String, Double> expensesByDate = new TreeMap<>();

            DateTimeFormatter formatter = getFormatterForPeriod(period);
            List<LocalDateTime> dates = generateDateRange(startDate, endDate, period);

            // Initialize maps with zero values
            dates.forEach(date -> {
                String formattedDate = date.format(formatter);
                incomeByDate.put(formattedDate, 0.0);
                expensesByDate.put(formattedDate, 0.0);
            });

            // Aggregate transactions with null checks
            transactions.stream()
                    .filter(transaction -> transaction.getCreatedAt() != null && transaction.getAmount() != null)
                    .forEach(transaction -> {
                        String date = transaction.getCreatedAt().format(formatter);
                        if ("INCOME".equals(transaction.getType())) {
                            incomeByDate.merge(date, transaction.getAmount(), Double::sum);
                        } else {
                            expensesByDate.merge(date, transaction.getAmount(), Double::sum);
                        }
                    });

            TrendData trendData = new TrendData();
            trendData.setIncomeData(mapToDateAmountPairs(incomeByDate));
            trendData.setExpenseData(mapToDateAmountPairs(expensesByDate));

            return ResponseEntity.ok(trendData);
        } catch (Exception e) {
            logger.error("Error fetching transaction trends for user {}: {}", userId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private List<DateAmountPair> mapToDateAmountPairs(Map<String, Double> data) {
        return data.entrySet().stream()
                .map(e -> new DateAmountPair(e.getKey(), e.getValue() != null ? e.getValue() : 0.0))
                .collect(Collectors.toList());
    }

    private DateTimeFormatter getFormatterForPeriod(String period) {
        return switch (period.toLowerCase()) {
            case "month" -> DateTimeFormatter.ofPattern("MMM dd");
            case "year" -> DateTimeFormatter.ofPattern("MMM");
            case "week" -> DateTimeFormatter.ofPattern("EEE");
            default -> DateTimeFormatter.ofPattern("EEE");
        };
    }

    private List<LocalDateTime> generateDateRange(LocalDateTime start, LocalDateTime end, String period) {
        List<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime current = start;

        while (!current.isAfter(end)) {
            dates.add(current);
            current = period.toLowerCase().equals("year") ? current.plusMonths(1) : current.plusDays(1);
        }
        return dates;
    }
}