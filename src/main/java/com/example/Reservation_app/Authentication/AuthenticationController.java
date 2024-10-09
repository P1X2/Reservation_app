//package com.example.Reservation_app.Authentication;
//
//import com.example.Reservation_app.Users.User.dto.UserLoginDTO;
//import com.example.Reservation_app.Users.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@AllArgsConstructor
//public class AuthenticationController {
//
//    private AuthenticationManager authenticationManager;
//    private UserService userService;
//
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody RegisterUserCommand registrationDto) {
//        if (userService.getByUsername(registrationDto.username()).isPresent()) {
//            return ResponseEntity.badRequest().body("Username is already taken");
//        }
//        userService.registerUser(registrationDto);
//        return ResponseEntity.ok("User registered successfully");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDTO loginDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                loginDto.username(),
//                loginDto.password()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return ResponseEntity.ok("User authenticated successfully");
//    }
//}
