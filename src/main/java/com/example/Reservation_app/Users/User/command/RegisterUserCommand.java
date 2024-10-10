package com.example.Reservation_app.Users.User.command;

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
    // @todo validator na dlugosc hasla i znaki spacjalne coby sie posral iz wrazenia
    @NotEmpty
    String password;
    @NotEmpty
    @Email
    String email;
    @NotEmpty
    String name;
    @NotEmpty
    String surname;
}
