package com.example.Reservation_app.Security;

import com.example.Reservation_app.Users.User.command.LoginUserCommand;
import com.example.Reservation_app.Users.User.command.RegisterUserCommand;
import com.example.Reservation_app.Users.User.dto.RegisterUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody @Valid RegisterUserCommand registerUserCommand) {
        return ResponseEntity.ok(authService.registerUser(registerUserCommand));
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginUserCommand loginUserCommand){
        return ResponseEntity.ok(authService.login(loginUserCommand));
    }
}
