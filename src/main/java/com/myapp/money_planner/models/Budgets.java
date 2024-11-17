package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budgets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    @ManyToOne
    @JoinColumn(name = "user_id_FK", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "category_id_FK", referencedColumnName = "category_id", nullable = false)
    private Categories category;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "budget_status", nullable = false, columnDefinition = "ENUM('active', 'completed', 'expired') DEFAULT 'active'")
    private String budgetStatus;
}
