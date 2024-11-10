package com.example.Reservation_app.Appointments.Appointment.mapper;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Appointments.Appointment.command.CreateAppointmentCommand;
import com.example.Reservation_app.Services.ServiceRepository;
import com.example.Reservation_app.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreateAppointmentCommandToAppointmentDtoMapper {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public Appointment map(CreateAppointmentCommand command){
        return Appointment.builder()
                .appointmentDate(command.getAppointmentDate())
                .status(AppointmentStatus.PENDING_PAYMENT)
                .service(serviceRepository.findById(command.getServiceId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found")))
                .client(userRepository.findById(command.getClientId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found")))
                .employee(userRepository.findById(4L)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"))) // todo tu dadac jakas karuzele do wyboru wolnego pracownika
                .build();
    }
}
