package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {
    List<Goals> findByUser_UserId(Long userId);

    Optional<Goals> findByGoalIdAndUser_UserId(Long goalId, Long userId);

    List<Goals> findByUser_UserIdAndGoalNameContainingIgnoreCase(Long userId, String goalName);
}
