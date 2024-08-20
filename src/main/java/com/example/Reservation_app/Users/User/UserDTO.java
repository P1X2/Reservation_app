package com.example.Reservation_app.Users.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDTO(

        String username,
        String password,
        @Email
        String email,
        String name,
        String surname)
{}
