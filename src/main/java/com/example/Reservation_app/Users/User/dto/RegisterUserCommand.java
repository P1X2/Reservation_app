package com.example.Reservation_app.Users.User.dto;

import jakarta.validation.constraints.Email;

public record RegisterUserCommand(

        String username,
        // @todo validator na dlugosc hasla i znaki spacjalne coby sie posral iz wrazenia
        String password,
        @Email
        String email,
        String name,
        String surname)
{}
