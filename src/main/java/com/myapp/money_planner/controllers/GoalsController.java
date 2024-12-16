package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Goals;
import com.myapp.money_planner.services.GoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:5173")
public class GoalsController {

    private final GoalsService goalsService;

    @Autowired
    public GoalsController(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    @PostMapping("/create")
    public ResponseEntity<Goals> createGoal(@RequestBody Goals goal) {
        Goals createdGoal = goalsService.createGoal(goal);
        return ResponseEntity.ok(createdGoal);
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
