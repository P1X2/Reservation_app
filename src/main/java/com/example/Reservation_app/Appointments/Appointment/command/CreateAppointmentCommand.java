package com.example.Reservation_app.Appointments.Appointment.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentCommand {

    private Long clientId;
    private Long employeeId;
    private Long serviceId;

    private LocalDateTime appointmentDate;
}
