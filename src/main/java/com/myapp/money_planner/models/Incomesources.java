package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "incomesources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incomesources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id")
    private Long sourceId;

    @ManyToOne
    @JoinColumn(name = "user_id_FK", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @Column(name = "source_name", nullable = false)
    private String sourceName;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
}
