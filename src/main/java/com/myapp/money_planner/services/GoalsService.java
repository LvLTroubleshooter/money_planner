package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Goals;
import com.myapp.money_planner.repositories.GoalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoalsService {

    @Autowired
    private GoalsRepository goalsRepository;

    // Create
    public Goals createGoal(Goals goal) {
        return goalsRepository.save(goal);
    }

    // Read
    public Optional<Goals> getGoalById(Long goalId) {
        return goalsRepository.findById(goalId);
    }

    public Optional<Goals> getGoalByUserId(Long userId) {
        return goalsRepository.findByUser_UserId(userId);
    }

    public Optional<Goals> getGoalByName(String goalName) {
        return goalsRepository.findByGoalName(goalName);
    }

    // Update
    public Goals updateGoal(Long goalId, Goals goal) {
        if (goalsRepository.existsById(goalId)) {
            goal.setGoalId(goalId); // Ensure we're updating the correct goal
            return goalsRepository.save(goal);
        }
        return null; // Return null if the goal doesn't exist
    }

    // Delete
    public boolean deleteGoal(Long goalId) {
        if (goalsRepository.existsById(goalId)) {
            goalsRepository.deleteById(goalId);
            return true;
        }
        return false; // Return false if the goal doesn't exist
    }
}
