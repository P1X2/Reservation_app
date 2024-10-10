package com.example.Reservation_app.Users.User.command;

import com.example.Reservation_app.Users.User.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatchUserRoleCommand {
    @NotNull
    Long userId;
    @NotNull
    UserRole userRole;
}
