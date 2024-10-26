package com.example.Reservation_app.Users.User.command;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserCommand {

    private String username;
    private String password;
}
