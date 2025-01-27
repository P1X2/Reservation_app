package com.example.Reservation_app.Appointments.Appointment;

import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChooseEmployeeService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Long chooseEmployee(){
        Map<User, Long> employeeBusiness = appointmentRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Appointment::getEmployee,
                        Collectors.counting()
                ));

        return employeeBusiness.entrySet().stream()
                .reduce((a,b) -> a.getValue() < b.getValue() ? a :b)
                .map(Map.Entry::getValue)
                .orElseGet(this::getPresidentId);
    }

    private Long getPresidentId(){
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(UserRole.PRESIDENT))
                .map(User::getUserId)
                .findAny().get();
    }
}
