package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @ManyToOne
    @JoinColumn(name = "user_id_FK", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @Column(name = "goal_name", nullable = false)
    private String goalName;

    @Column(name = "goal_amount", nullable = false)
    private Double goalAmount;

    @Column(name = "current_amount", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private Double currentAmount;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
}
