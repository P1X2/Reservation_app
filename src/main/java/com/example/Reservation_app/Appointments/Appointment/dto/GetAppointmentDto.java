package com.example.Reservation_app.Appointments.Appointment.dto;

import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Services.Service;
import com.example.Reservation_app.Users.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAppointmentDto{

        private Long appointment_id;

        private LocalDateTime appointment_date;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedOn;

        private AppointmentStatus status;

        private Ser service;
        private User client;
        private User employee;
}
