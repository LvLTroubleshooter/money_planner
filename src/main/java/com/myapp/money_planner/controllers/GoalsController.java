package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Goals;
import com.myapp.money_planner.services.GoalsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:5173")
public class GoalsController {

    private static final Logger logger = LoggerFactory.getLogger(GoalsController.class);

    private final GoalsService goalsService;

    @Autowired
    public GoalsController(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGoal(@RequestBody Goals goal) {
        try {
            logger.info("Received request to create goal: {}", goal);
            logger.info("Goal amount: {}", goal.getGoalAmount());
            logger.info("Current amount: {}", goal.getCurrentAmount());
            logger.info("User ID: {}", goal.getUser().getUserId());

            if (goal.getUser() == null || goal.getUser().getUserId() == null) {
                logger.error("User information is missing in the request");
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "User information is required");
                            }
                        });
            }

            if (goal.getGoalAmount() == null || goal.getGoalAmount() <= 0) {
                logger.error("Invalid goal amount: {}", goal.getGoalAmount());
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "Goal amount must be greater than 0");
                            }
                        });
            }

            Goals newGoal = goalsService.createGoal(goal);
            logger.info("Created goal: {}", newGoal);
            return ResponseEntity.ok(newGoal);
        } catch (Exception e) {
            logger.error("Error creating goal: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {
                        {
                            put("error", "Error creating goal: " + e.getMessage());
                        }
                    });
        }
    }

    @GetMapping("/{goalId}")
    public ResponseEntity<Goals> getGoalById(@PathVariable Long goalId) {
        return goalsService.getGoalById(goalId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Goals>> getGoalsByUserId(@PathVariable Long userId) {
        List<Goals> goals = goalsService.getGoalsByUserId(userId);
        if (!goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}/name/{goalName}")
    public ResponseEntity<List<Goals>> getGoalsByUserIdAndGoalName(@PathVariable Long userId,
            @PathVariable String goalName) {
        List<Goals> goals = goalsService.getGoalsByUserIdAndGoalName(userId, goalName);
        if (!goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/user/{userId}/goal/{goalId}")
    public ResponseEntity<Goals> updateGoal(@PathVariable Long userId, @PathVariable Long goalId,
            @RequestBody Goals goal) {
        Goals updatedGoal = goalsService.updateGoal(userId, goalId, goal);
        return updatedGoal != null ? ResponseEntity.ok(updatedGoal) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}/goal/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long userId, @PathVariable Long goalId) {
        boolean deleted = goalsService.deleteGoal(goalId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
