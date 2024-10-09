package com.example.Reservation_app.Users.User.dto;

import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterUserResponseDto {

    private String username;
    private UserStatus userStatus;
    private UserRole role;


}
