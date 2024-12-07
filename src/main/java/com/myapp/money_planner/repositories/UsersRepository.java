package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Users;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @NonNull
    Optional<Users> findByUsername(@NonNull String username);

    @NonNull
    Optional<Users> findByEmail(@NonNull String email);

    Optional<Users> findByUsernameOrEmail(String username, String email);

    @NonNull
    Optional<Users> findById(@NonNull Long userId);
}
