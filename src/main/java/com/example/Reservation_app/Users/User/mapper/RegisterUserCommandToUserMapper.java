package com.example.Reservation_app.Users.User.mapper;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import com.example.Reservation_app.Users.User.command.RegisterUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RegisterUserCommandToUserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    public User map(RegisterUserCommand command){
        return User.builder()
                .name(command.getName())
                .surname(command.getSurname())
                .password(passwordEncoder.encode(command.getPassword()))
                .username(command.getPassword())
                .email(command.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .role(UserRole.CLIENT)
                .createdAt(LocalDateTime.now())
                .modifiedOn(LocalDateTime.now())
                .build();
    }

}
