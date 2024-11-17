package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Incomesources;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomesourcesRepository extends JpaRepository<Incomesources, Long> {
    @NonNull
    Optional<Incomesources> findBySourceName(@NonNull String sourceName); // source_name is NOT NULL

    @NonNull
    Optional<Incomesources> findByUser_UserId(@NonNull Long userId);

    @NonNull
    Optional<Incomesources> findById(@NonNull Long sourceId);
}
