package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findTop4ByUserIdOrderByCreatedAtDesc(Long userId);

    List<Transactions> findByUserIdOrderByCreatedAtDesc(Long userId);
}