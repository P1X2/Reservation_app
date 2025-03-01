package com.example.Reservation_app.Users.User.command;

import com.example.Reservation_app.Users.validator.ValidPassword;
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
    @ValidPassword
    private String password;
    @Email
    private String email;
    private String name;
    private String surname;
}
