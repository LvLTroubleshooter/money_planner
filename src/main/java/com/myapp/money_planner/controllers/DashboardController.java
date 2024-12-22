package com.myapp.money_planner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myapp.money_planner.repositories.IncomesourcesRepository;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
public class DashboardController {

    @Autowired
    private IncomesourcesRepository incomesourcesRepository;

    @GetMapping("/income/{userId}")
    public ResponseEntity<Double> getTotalIncome(@PathVariable Long userId) {
        try {
            System.out.println("Fetching total income for user ID: " + userId);
            if (userId == null) {
                return ResponseEntity.badRequest().build();
            }
            double totalIncome = incomesourcesRepository.getTotalIncomeByUserId(userId);
            System.out.println("Total income calculated: " + totalIncome);
            return ResponseEntity.ok(totalIncome);
        } catch (Exception e) {
            System.err.println("Error calculating total income: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}