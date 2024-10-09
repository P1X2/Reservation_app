package com.example.Reservation_app.Users.User.mapper;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import com.example.Reservation_app.Users.User.dto.RegisterUserCommand;

import java.time.LocalDateTime;

public class RegisterUserCommandToUserMapper {

    public User map(RegisterUserCommand registerUserCommand){
        return User.builder()
                .name(registerUserCommand.getName())
                .surname(registerUserCommand.getSurname())
                .password(registerUserCommand.getPassword())
                .username(registerUserCommand.getPassword())
                .email(registerUserCommand.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .role(UserRole.CLIENT)
                .createdAt(LocalDateTime.now())
                .build();
    }

}