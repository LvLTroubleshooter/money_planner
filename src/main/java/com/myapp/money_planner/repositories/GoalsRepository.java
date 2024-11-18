package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Goals;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {

    @NonNull
    List<Goals> findByUser_UserId(@NonNull Long userId);

    @NonNull
    List<Goals> findByUser_UserIdAndGoalName(@NonNull Long userId, @NonNull String goalName);

    @NonNull
    Optional<Goals> findByUser_UserIdAndGoalId(@NonNull Long userId, @NonNull Long goalId);
}
