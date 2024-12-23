package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions_view")
@Data
public class Transactions {
    @Id
    private Long id;

    private String type; // "INCOME" or "EXPENSE"
    private String name; // source_name for INCOME, category_name for EXPENSE
    private Double amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "user_id")
    private Long userId;
}