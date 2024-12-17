package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_FK", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id_FK", nullable = false)
    private Categories category;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
}
