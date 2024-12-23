package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Transactions;
import com.myapp.money_planner.repositories.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionsController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    private TransactionsRepository transactionsRepository;

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
}