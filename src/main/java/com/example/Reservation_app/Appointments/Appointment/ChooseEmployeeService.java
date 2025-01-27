package com.example.Reservation_app.Appointments.Appointment;

import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.UserRole;
import com.example.Reservation_app.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChooseEmployeeService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Long chooseEmployee(){
        Map<Long, Integer> employeeBusiness = userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(UserRole.EMPLOYEE))
                .collect(Collectors.toMap(
                        User::getUserId,
                        user -> user.getAppointmentList().size()
                ));

        return employeeBusiness.entrySet().stream()
                .reduce((a,b) -> a.getValue() < b.getValue() ? a :b)
                .map(Map.Entry::getKey)
                .orElseGet(this::getPresidentId);
    }

    private Long getPresidentId(){
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(UserRole.PRESIDENT))
                .map(User::getUserId)
                .findAny().get();
    }
}
