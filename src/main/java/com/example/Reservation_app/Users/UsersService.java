package com.example.Reservation_app.Users;

import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Appointments.AppointmentService;
import com.example.Reservation_app.Users.User.command.*;
import com.example.Reservation_app.Users.User.dto.*;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.mapper.RegisterUserCommandToUserMapper;
import com.example.Reservation_app.Users.User.mapper.UserToGetUserDtoMapper;
import com.example.Reservation_app.Users.User.mapper.UserToPatchUserResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final RegisterUserCommandToUserMapper registerUserCommandToUserMapper;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;
    private final UserToPatchUserResponseDtoMapper userToPatchUserResponseDtoMapper;


    GetUserDto getById(Long userId) {
        return userRepository.findById(userId)
                .map(userToGetUserDtoMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    public PatchUserResponseDto patchUser(PatchUserCommand command){
        User user = getUserByIdInternal(command.getUserId());

        Optional.ofNullable(command.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(command.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(command.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(command.getName()).ifPresent(user::setName);
        Optional.ofNullable(command.getSurname()).ifPresent(user::setSurname);
        user.setModifiedOn(LocalDateTime.now());

        userRepository.save(user);

        return userToPatchUserResponseDtoMapper.map(user);

    }

    public void patchUserRole(PatchUserRoleCommand command){
        User user = getUserByIdInternal(command.getUserId());
        user.setRole(command.getUserRole());
        user.setModifiedOn(LocalDateTime.now());

        userRepository.save(user);
    }

    public void patchUserStatus(PatchUserStatusCommand command){
    User user = getUserByIdInternal(command.getUserId());
    user.setUserStatus(command.getUserStatus());
    user.setModifiedOn(LocalDateTime.now());

    userRepository.save(user);
    }

    public void changePassword(SetUserPasswordCommand command){
        User user = getUserByIdInternal(command.getUserId());
        user.setPassword(command.getPassword());
        user.setModifiedOn(LocalDateTime.now());

        userRepository.save(user);
    }

    public void delete(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        appointmentRepository.findByClient(user)
                .forEach(bandedAppointment -> appointmentService.delete(bandedAppointment.getAppointmentId()));

        userRepository.delete(user);
    }

    private User getUserByIdInternal(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
