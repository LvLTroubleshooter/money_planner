package com.myapp.money_planner.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "profile_photo_name")
    private String profilePhotoName;

    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;

    // Transient fields for password change
    @Transient
    private String currentPassword;

    @Transient
    private String newPassword;

    @Transient
    private String confirmPassword;
}
