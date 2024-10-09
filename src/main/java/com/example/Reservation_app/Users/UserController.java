package com.example.Reservation_app.Users;

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
    User getById(@RequestParam Long userId) {
        return userService.getById(userId);
    }

    @PostMapping("/register")
    ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody @Valid RegisterUserCommand registerUserCommand) {
        return ResponseEntity.ok(userService.registerUser(registerUserCommand));
    }
// @ todo to kazdy siebie
    @PatchMapping("/patch-user-data")
    public ResponseEntity<PatchUserResponseDto> patchUser(@RequestBody PatchUserCommand command){
        return ResponseEntity.ok(userService.patchUser(command));
    }

    // @ todo to tylko admin
    @PatchMapping("/patch-user-role")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserRole(@RequestBody PatchUserRoleCommand command){
        userService.patchUserRole(command);
    }

    //@ todo to admin + robol , ale robol z mniejszymi prawami
    @PatchMapping("/patch-user-status")
    public void patchUserStatus(@RequestBody PatchUserStatusCommand command){
        userService.patchUserStatus(command);
    }

    @DeleteMapping("/delete-user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam Long userId) {
        userService.delete(userId);
    }


}
