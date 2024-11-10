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
public class SetUserPasswordCommand {

    @NotNull
    private Long userId;
    @NotNull
    private String oldPassword;
    @NotNull
    @ValidPassword
    private String newPassword;
}

