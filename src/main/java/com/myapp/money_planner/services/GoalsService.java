package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Goals;
import com.myapp.money_planner.repositories.GoalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GoalsService {

    @Autowired
    private GoalsRepository goalsRepository;

    public Goals createGoal(Goals goal) {
        if (goal.getCreatedAt() == null) {
            goal.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        }
        return goalsRepository.save(goal);
    }

    public Optional<Goals> getGoalById(Long goalId) {
        return goalsRepository.findById(goalId);
    }

    public List<Goals> getGoalsByUserId(Long userId) {
        return goalsRepository.findByUser_UserId(userId);
    }

    public List<Goals> getGoalsByUserIdAndGoalName(Long userId, String goalName) {
        return goalsRepository.findByUser_UserIdAndGoalName(userId, goalName);
    }

    public Goals updateGoal(Long userId, Long goalId, Goals goal) {
        Optional<Goals> existingGoal = goalsRepository.findByUser_UserIdAndGoalId(userId, goalId);

        if (existingGoal.isPresent()) {
            Goals goalToUpdate = existingGoal.get();

            // Prevent updating the user or goal ID
            goalToUpdate.setGoalName(goal.getGoalName());
            goalToUpdate.setGoalAmount(goal.getGoalAmount());
            goalToUpdate.setCurrentAmount(goal.getCurrentAmount());
            goalToUpdate.setDeadline(goal.getDeadline());

            return goalsRepository.save(goalToUpdate);
        }

        return null;
    }

    public boolean deleteGoal(Long goalId) {
        if (goalsRepository.existsById(goalId)) {
            goalsRepository.deleteById(goalId);
            return true;
        }
        return false;
    }
}
