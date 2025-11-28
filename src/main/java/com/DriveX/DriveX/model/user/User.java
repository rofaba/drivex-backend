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

    private String first_name;

    private String last_name;

    private String phone_number;

    private String role;

    private Boolean is_active;

    private Timestamp created_at;

    private Timestamp updated_at;

    private String profile_image;
}