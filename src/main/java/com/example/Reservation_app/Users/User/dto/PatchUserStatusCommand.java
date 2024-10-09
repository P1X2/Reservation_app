package com.example.Reservation_app.Users.User.dto;


import com.example.Reservation_app.Users.User.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatchUserStatusCommand {
    @NotNull
    Long userId;
    @NotNull
    UserStatus userStatus;
}
