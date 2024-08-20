package com.example.Reservation_app.Appointments.Appointment;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDTO(
        @NotNull
        Long serviceId,
        @NotNull
        Long clientId,
        @NotNull
        Long employeeId,
        @NotNull
        LocalDateTime appointmentId
)
{}
