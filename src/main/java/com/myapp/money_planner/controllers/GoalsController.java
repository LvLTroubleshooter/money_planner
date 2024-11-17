package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Goals;
import com.myapp.money_planner.services.GoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    private final GoalsService goalsService;

    @Autowired
    public GoalsController(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Goals> createGoal(@RequestBody Goals goal) {
        Goals createdGoal = goalsService.createGoal(goal);
        return ResponseEntity.ok(createdGoal);
    }

    // Read by ID
    @GetMapping("/{goalId}")
    public ResponseEntity<Goals> getGoalById(@PathVariable Long goalId) {
        Optional<Goals> goal = goalsService.getGoalById(goalId);
        return goal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Goals> getGoalByUserId(@PathVariable Long userId) {
        Optional<Goals> goal = goalsService.getGoalByUserId(userId);
        return goal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by Goal Name
    @GetMapping("/name/{goalName}")
    public ResponseEntity<Goals> getGoalByName(@PathVariable String goalName) {
        Optional<Goals> goal = goalsService.getGoalByName(goalName);
        return goal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{goalId}")
    public ResponseEntity<Goals> updateGoal(@PathVariable Long goalId, @RequestBody Goals goal) {
        Goals updatedGoal = goalsService.updateGoal(goalId, goal);
        return updatedGoal != null ? ResponseEntity.ok(updatedGoal) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        boolean deleted = goalsService.deleteGoal(goalId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
