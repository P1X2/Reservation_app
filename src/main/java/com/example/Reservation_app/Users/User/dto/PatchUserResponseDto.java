package com.example.Reservation_app.Users.User.dto;

import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatchUserResponseDto {

    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;

    private UserStatus userStatus;
    private UserRole role;

    private LocalDateTime modifiedOn;

}
