package com.example.Reservation_app.Users.User.mapper;

import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.dto.GetUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserDtoMapper {
    // bez hasla
    public GetUserDto map(User user){
        return GetUserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .userStatus(user.getUserStatus())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .modifiedOn(user.getModifiedOn())
                .build();
    }
}
