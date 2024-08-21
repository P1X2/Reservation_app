package com.example.Reservation_app.Users;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserDTO;
import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get_by_id")
    User getById(@RequestParam Long userId)
    {
        return userService.getById(userId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    void addNew(@RequestBody @Valid UserDTO userDTO)
    {
        userService.addNew(userDTO);
    }

    @PutMapping("/update_status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateStatus(
            @RequestParam Long userId,
            @RequestParam UserStatus newStatus)
    {
        userService.updateStatus(userId, newStatus);
    }
    @PutMapping("/update_role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateRole(
            @RequestParam Long userId,
            @RequestParam UserRole newRole)
    {
        userService.updateRole(userId, newRole);
    }

    @PutMapping("/update_password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updatePassword(
            @RequestParam Long userId,
            @RequestParam String newPassword)
    {
        userService.updatePassword(userId, newPassword);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam Long userId)
    {
        userService.delete(userId);
    }


}
