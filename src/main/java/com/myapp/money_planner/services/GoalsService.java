package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Goals;
import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.repositories.GoalsRepository;
import com.myapp.money_planner.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalsService {
    private static final Logger logger = LoggerFactory.getLogger(GoalsService.class);

    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Goals createGoal(Goals goal) {
        try {
            logger.info("Creating goal with data: goalName={}, goalAmount={}, currentAmount={}, deadline={}, userId={}",
                    goal.getGoalName(),
                    goal.getGoalAmount(),
                    goal.getCurrentAmount(),
                    goal.getDeadline(),
                    goal.getUser().getUserId());

            Users user = usersRepository.findById(goal.getUser().getUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found with ID: {}", goal.getUser().getUserId());
                        return new RuntimeException("User not found");
                    });

            if (goal.getCurrentAmount() == null) {
                goal.setCurrentAmount(0.0);
            }

            goal.setUser(user);

            Goals savedGoal = goalsRepository.save(goal);
            logger.info("Successfully created goal with ID: {}", savedGoal.getGoalId());
            return savedGoal;
        } catch (Exception e) {
            logger.error("Error creating goal: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating goal: " + e.getMessage());
        }
    }

    public List<Goals> getGoalsByUserId(Long userId) {
        return goalsRepository.findByUser_UserId(userId);
    }

    public Optional<Goals> getGoalById(Long goalId) {
        return goalsRepository.findById(goalId);
    }

    public List<Goals> getGoalsByUserIdAndGoalName(Long userId, String goalName) {
        return goalsRepository.findByUser_UserIdAndGoalNameContainingIgnoreCase(userId, goalName);
    }

    public Goals updateGoal(Long userId, Long goalId, Goals updatedGoal) {
        return goalsRepository.findByGoalIdAndUser_UserId(goalId, userId)
                .map(existingGoal -> {
                    existingGoal.setGoalName(updatedGoal.getGoalName());
                    existingGoal.setGoalAmount(updatedGoal.getGoalAmount());
                    existingGoal.setCurrentAmount(updatedGoal.getCurrentAmount());
                    existingGoal.setDeadline(updatedGoal.getDeadline());
                    return goalsRepository.save(existingGoal);
                })
                .orElse(null);
    }

    public boolean deleteGoal(Long goalId) {
        if (goalsRepository.existsById(goalId)) {
            goalsRepository.deleteById(goalId);
            return true;
        }
        return false;
    }

    public List<Goals> getLatestGoals(Long userId) {
        try {
            List<Goals> goals = goalsRepository.findTop3ByUser_UserIdOrderByCreatedAtDesc(userId);
            return goals != null ? goals : new ArrayList<>();
        } catch (Exception e) {
            logger.error("Error fetching latest goals for user {}: {}", userId, e.getMessage());
            return new ArrayList<>();
        }
    }
}
