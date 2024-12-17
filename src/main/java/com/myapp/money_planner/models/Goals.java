package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Goals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_FK", nullable = false)
    private Users user;

    @Column(name = "goal_name", nullable = false)
    private String goalName;

    @Basic
    @Column(name = "goal_amount")
    private Double goalAmount;

    @Basic
    @Column(name = "current_amount")
    private Double currentAmount = 0.0;

    @Basic
    @Column(name = "deadline")
    private LocalDate deadline;

    @Basic
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        if (currentAmount == null) {
            currentAmount = 0.0;
        }
    }
}
