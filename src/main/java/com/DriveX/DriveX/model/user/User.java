package com.DriveX.DriveX.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password_hash;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "first_name")
    private String lastname;

    private String phone_number;

    private String role;

    private Boolean is_active;

    private Timestamp created_at;

    private Timestamp updated_at;

    @Column(name = "profile_image")
    private String profileImage;
}