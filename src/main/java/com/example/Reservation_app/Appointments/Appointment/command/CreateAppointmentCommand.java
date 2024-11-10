package com.example.Reservation_app.Appointments.Appointment.command;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentCommand {

    @NotNull
    private Long clientId;
    private Long employeeId;
    @NotNull
    private Long serviceId;
    @NotNull
    private LocalDateTime appointmentDate;
}
