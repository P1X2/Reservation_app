package com.example.Reservation_app.Appointments.Appointment.dto;

import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Services.Service.Service;
import com.example.Reservation_app.Services.Service.dto.GetServiceDto;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.User.dto.GetUserDto;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAppointmentDto{

        private Long appointmentId;

        private LocalDateTime appointmentDate;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedOn;

        private AppointmentStatus status;

        private GetServiceDto service;
        private GetUserDto client;
        private GetUserDto employee;
}
