package com.example.Reservation_app.security;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.command.LoginUserCommand;
import com.example.Reservation_app.Users.User.command.RegisterUserCommand;
import com.example.Reservation_app.Users.User.dto.GetUserDto;
import com.example.Reservation_app.Users.User.dto.RegisterUserResponseDto;
import com.example.Reservation_app.Users.User.mapper.RegisterUserCommandToUserMapper;
import com.example.Reservation_app.Users.UserRepository;
import com.example.Reservation_app.security.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisterUserCommandToUserMapper registerUserCommandToUserMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterUserResponseDto registerUser(RegisterUserCommand command){
        User newUser = registerUserCommandToUserMapper.map(command);

        userRepository.save(newUser);

        return RegisterUserResponseDto.builder()
                .username(newUser.getUsername())
                .userStatus(newUser.getUserStatus())
                .role(newUser.getRole())
                .build();
    }

    public boolean login(LoginUserCommand command){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword())
        );
        if(auth.isAuthenticated()){
            return jwtService.generateToken(command.getUsername());
        }

    }
}
