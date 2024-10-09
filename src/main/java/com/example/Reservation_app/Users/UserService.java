package com.example.Reservation_app.Users;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Appointments.AppointmentService;
import com.example.Reservation_app.Users.User.dto.*;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.mapper.RegisterUserCommandToUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
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
        User user = getById(command.getUserId());

        Optional.ofNullable(command.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(command.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(command.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(command.getName()).ifPresent(user::setName);
        Optional.ofNullable(command.getSurname()).ifPresent(user::setSurname);
        user.setModifiedOn(LocalDateTime.now());

        userRepository.save(user);

        return PatchUserResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .userStatus(user.getUserStatus())
                .role(user.getRole())
                .build();

    }

    public void patchUserRole(PatchUserRoleCommand command){
        User user = getById(command.getUserId());
        user.setRole(command.getUserRole());
        user.setModifiedOn(LocalDateTime.now());

        userRepository.save(user);
    }

    public void patchUserStatus(PatchUserStatusCommand command){
    User user = getById(command.getUserId());
    user.setUserStatus(command.getUserStatus());
    user.setModifiedOn(LocalDateTime.now());

    userRepository.save(user);
    }

// @todo refactor to gowno
    public void delete(Long userId)
    {
        User user = userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Appointment> bandedAppointments = appointmentRepository.findByClient(user);

        // nie moze byc zwykly deletAll, bo na app jest reference z review ~ moze custom deleteAll, który przyjmuje liste app
        for (Appointment app : bandedAppointments)
        {
            appointmentService.delete(app.getAppointment_id());
        }

        userRepository.delete(user);
    }

}
