package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "user_id_FK", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @Column(name = "category_name", nullable = false)
    private String categoryName;
}
