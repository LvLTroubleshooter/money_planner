package com.myapp.money_planner.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_FK", nullable = false)
    @JsonIgnoreProperties({ "password", "createdAt", "expenses", "categories" })
    private Users user;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "created_at", nullable = true, columnDefinition = "TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "icon")
    private String icon;

    @Column(name = "color")
    private String color;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }
    }
}
