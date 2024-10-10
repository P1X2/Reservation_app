package com.example.Reservation_app.Users.User.mapper;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.dto.GetUserDto;
import com.example.Reservation_app.Users.User.dto.PatchUserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserToPatchUserResponseDtoMapper {

    public PatchUserResponseDto map (User user){
        return PatchUserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .userStatus(user.getUserStatus())
                .role(user.getRole())
                .modifiedOn(user.getModifiedOn())
                .build();
    }
}
