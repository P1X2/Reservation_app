package com.example.Reservation_app.Users;

import com.example.Reservation_app.Users.User.command.PatchUserCommand;
import com.example.Reservation_app.Users.User.command.PatchUserRoleCommand;
import com.example.Reservation_app.Users.User.command.PatchUserStatusCommand;
import com.example.Reservation_app.Users.User.command.RegisterUserCommand;
import com.example.Reservation_app.Users.User.dto.*;
import com.example.Reservation_app.Users.User.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get_by_id")
    GetUserDto getById(@RequestParam Long userId) {
        return userService.getById(userId);
    }

    @PostMapping("/register")
    ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody @Valid RegisterUserCommand registerUserCommand) {
        return ResponseEntity.ok(userService.registerUser(registerUserCommand));
    }
// @ todo to kazdy siebie
    @PatchMapping("/patch-user-data")
    public ResponseEntity<PatchUserResponseDto> patchUser(@RequestBody @Valid PatchUserCommand command){
        return ResponseEntity.ok(userService.patchUser(command));
    }

    // @ todo to tylko admin
    @PatchMapping("/patch-role")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserRole(@RequestBody @Valid PatchUserRoleCommand command){
        userService.patchUserRole(command);
    }

    //@ todo to admin + robol , ale robol z mniejszymi prawami
    @PatchMapping("/patch-status")
    public void patchUserStatus(@RequestBody @Valid PatchUserStatusCommand command){
        userService.patchUserStatus(command);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam Long userId) {
        userService.delete(userId);
    }


}
