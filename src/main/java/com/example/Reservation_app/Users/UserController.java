package com.example.Reservation_app.Users;

import com.example.Reservation_app.Users.User.command.*;
import com.example.Reservation_app.Users.User.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UsersService usersService;

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/get_by_id")
    GetUserDto getById(@RequestParam Long userId) {
        return usersService.getById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @PatchMapping("/patch-user-data")
    public ResponseEntity<PatchUserResponseDto> patchUser(@RequestBody @Valid PatchUserCommand command){
        return ResponseEntity.ok(usersService.patchUser(command));
    }

    @PreAuthorize("hasAuthority('ROLE_PRESIDENT')")
    @PatchMapping("/change-role")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserRole(@RequestBody @Valid PatchUserRoleCommand command){
        usersService.patchUserRole(command);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE')")
    @PatchMapping("/change-status")
    @ResponseStatus(HttpStatus.OK)
    public void patchUserStatus(@RequestBody @Valid PatchUserStatusCommand command){
        usersService.patchUserStatus(command);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @PutMapping("change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid SetUserPasswordCommand command){
        usersService.changePassword(command);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam Long userId) {
        usersService.delete(userId);
    }


}
