package com.example.Reservation_app.Users;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Appointments.AppointmentService;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserDTO;
import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.User.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;


    User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    void addNew(UserDTO userDTO){

        User newUser = new User();
        newUser.setName(userDTO.name());
        newUser.setSurname(userDTO.surname());
        newUser.setPassword(userDTO.password());
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());

        newUser.setUser_status(UserStatus.ACTIVE);
        newUser.setRole(UserRole.CLIENT);
        newUser.setCreated_At(LocalDateTime.now());

        userRepository.save(newUser);
    }

    void updateStatus(Long userId, UserStatus newStatus){

        User user = userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setUser_status(newStatus);
        userRepository.save(user);
    }

    void updateRole(Long userId, UserRole newRole){

        User user = userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setRole(newRole);
        userRepository.save(user);
    }

    void updatePassword(Long userId, String newPassword){

        User user = userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    void delete(Long userId)
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
