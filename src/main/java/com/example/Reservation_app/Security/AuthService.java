package com.example.Reservation_app.Security;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.command.LoginUserCommand;
import com.example.Reservation_app.Users.User.command.RegisterUserCommand;
import com.example.Reservation_app.Users.User.dto.RegisterUserResponseDto;
import com.example.Reservation_app.Users.User.mapper.RegisterUserCommandToUserMapper;
import com.example.Reservation_app.Users.UserRepository;
import com.example.Reservation_app.Security.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisterUserCommandToUserMapper registerUserCommandToUserMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final String USER_PRINCIPAL_NOT_FOUND_MSG = "User with username %s and given password not found";

    public RegisterUserResponseDto registerUser(RegisterUserCommand command){
        User newUser = registerUserCommandToUserMapper.map(command);

        userRepository.save(newUser);

        return RegisterUserResponseDto.builder()
                .username(newUser.getUsername())
                .userStatus(newUser.getUserStatus())
                .role(newUser.getRole())
                .build();
    }

    public String login(LoginUserCommand command) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword())
        );

        if(auth.isAuthenticated()){
            return jwtService.generateToken(userRepository.findByUsername(command.getUsername()).orElseThrow(() -> new UsernameNotFoundException("user not found")));
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,  String.format(USER_PRINCIPAL_NOT_FOUND_MSG, command.getUsername()));
        }

    }
}
