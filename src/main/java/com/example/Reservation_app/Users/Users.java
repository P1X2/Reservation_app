package com.example.Reservation_app.Users;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private Long id;

    private UserRole role;
    private String username;
    private String password;
    @Email
    private String email;
    private String name;
    private String surname;
    private UserStatus userStatus;
    private LocalDateTime createdAt;



}
