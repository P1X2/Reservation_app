package com.example.Reservation_app.Users.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUserCommand {

    @NotNull
    private Long userId;

    private String username;
    private String password;
    @Email
    private String email;
    private String name;
    private String surname;
}
