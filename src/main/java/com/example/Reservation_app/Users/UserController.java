package com.example.Reservation_app.Users;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserDTO;
import com.example.Reservation_app.Users.User.UserStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/get_by_id")
    User getUserById(@RequestParam Long userId)
    {
        return userService.getUserById(userId);
    }

    //todo strnicowanie
//    @GetMapping("/get_by_status")

    @PostMapping("/add_user")
    @ResponseStatus(HttpStatus.CREATED)
    void addUser(@RequestBody @Valid UserDTO userDTO)
    {
        userService.addUser(userDTO);
    }

    @PutMapping("/change_user_status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changeUserStatus(@RequestParam Long userId, @RequestParam UserStatus newStatus)
    {
        userService.changeUserStatus(userId, newStatus);
    }

    @PutMapping("/change_user_password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changeUserDetails(@RequestParam Long userId, @RequestParam String newPassword)
    {
        userService.changeUserPassword(userId, newPassword);
    }

    @DeleteMapping("/delete_user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@RequestParam Long userId)
    {
        userService.deleteUser(userId);
    }


}
