package com.example.Reservation_app.Users;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Appointments.AppointmentService;
import com.example.Reservation_app.Users.User.command.PatchUserCommand;
import com.example.Reservation_app.Users.User.command.PatchUserRoleCommand;
import com.example.Reservation_app.Users.User.command.PatchUserStatusCommand;
import com.example.Reservation_app.Users.User.dto.*;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.mapper.RegisterUserCommandToUserMapper;
import com.example.Reservation_app.Users.User.mapper.UserToGetUserDtoMapper;
import com.example.Reservation_app.Users.User.mapper.UserToPatchUserResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.Reservation_app.Users.User.command.RegisterUserCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

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

    public RegisterUserResponseDto registerUser(RegisterUserCommand command){
        User newUser = registerUserCommandToUserMapper.map(command);

        userRepository.save(newUser);

        return RegisterUserResponseDto.builder()
                .username(newUser.getUsername())
                .userStatus(newUser.getUserStatus())
                .role(newUser.getRole())
                .build();
    }

    public PatchUserResponseDto patchUser(PatchUserCommand command){
        User user = getUserByIdInternal(command.getUserId());

        // todo move to patch calss, albo nie wyjebane
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

// @todo refactor to gowno
    public void delete(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Appointment> bandedAppointments = appointmentRepository.findByClient(user);

        bandedAppointments
                .forEach(bandedAppointment -> appointmentService.delete(bandedAppointment.getAppointment_id()));

//        // nie moze byc zwykly deletAll, bo na app jest reference z review ~ moze custom deleteAll, który przyjmuje liste app
//        for (Appointment app : bandedAppointments)
//        {
//            appointmentService.delete(app.getAppointment_id());
//        }
//
//        userRepository.delete(user);
    }

    private User getUserByIdInternal(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
