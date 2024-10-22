package com.example.Reservation_app.Users.User.dto;

import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserDto {

    private Long userId;

    private String username;
    private String email;
    private String name;
    private String surname;

    private UserStatus userStatus;
    private UserRole role;


    private LocalDateTime createdAt;
    private LocalDateTime modifiedOn;

}
