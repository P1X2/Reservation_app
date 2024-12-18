package com.example.Reservation_app.Users.User.command;

import com.example.Reservation_app.Users.validator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserCommand {
    @NotEmpty
    String username;
    @NotEmpty
    @ValidPassword
    String password;
    @NotEmpty
    @Email
    String email;
    @NotEmpty
    String name;
    @NotEmpty
    String surname;
}
