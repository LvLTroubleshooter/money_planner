package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Incomesources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;

@Repository
public interface IncomesourcesRepository extends JpaRepository<Incomesources, Long> {

    @NonNull
    List<Incomesources> findByUser_UserId(@NonNull Long userId);

    @NonNull
    List<Incomesources> findByUser_UserIdAndSourceName(@NonNull Long userId, @NonNull String sourceName);

    @NonNull
    Optional<Incomesources> findByUser_UserIdAndSourceId(@NonNull Long userId, @NonNull Long sourceId);

    @Query("SELECT COALESCE(SUM(i.amount), 0.0) FROM Incomesources i WHERE i.user.userId = :userId")
    double getTotalIncomeByUserId(@Param("userId") Long userId);
}
