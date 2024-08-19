package com.example.Reservation_app.Users.User;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private UserRole role;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Email
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    private UserStatus userStatus;
    private LocalDateTime createdAt;



}
