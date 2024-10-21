package com.example.Reservation_app.Users;

import com.example.Reservation_app.Users.User.command.*;
import com.example.Reservation_app.Users.User.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UsersService usersService;

    @GetMapping("/get_by_id")
    GetUserDto getById(@RequestParam Long userId) {
        return usersService.getById(userId);
    }

// @ todo to kazdy siebie
    @PatchMapping("/patch-user-data")
    public ResponseEntity<PatchUserResponseDto> patchUser(@RequestBody @Valid PatchUserCommand command){
        return ResponseEntity.ok(usersService.patchUser(command));
    }

    // @ todo to tylko admin
    @PatchMapping("/change-role")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserRole(@RequestBody @Valid PatchUserRoleCommand command){
        usersService.patchUserRole(command);
    }

    //@ todo to admin + robol , ale robol z mniejszymi prawami
    @PatchMapping("/change-status")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserStatus(@RequestBody @Valid PatchUserStatusCommand command){
        usersService.patchUserStatus(command);
    }

    @PutMapping("change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid SetUserPasswordCommand command){
        usersService.changePassword(command);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam Long userId) {
        usersService.delete(userId);
    }


}
