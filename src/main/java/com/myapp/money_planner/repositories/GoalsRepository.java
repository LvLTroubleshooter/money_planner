package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Goals;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {
    @NonNull
    Optional<Goals> findByUser_UserId(@NonNull Long userId); // user_id_FK is NOT NULL

    @NonNull
    Optional<Goals> findByGoalName(@NonNull String goalName); // goal_name is NOT NULL

    @NonNull
    Optional<Goals> findById(@NonNull Long goalId);
}
