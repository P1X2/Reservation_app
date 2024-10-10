package com.example.Reservation_app.Appointments.Appointment.mapper;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
import com.example.Reservation_app.Services.Service.mapper.ServiceToGetServiceDtoMapper;
import com.example.Reservation_app.Users.User.mapper.UserToGetUserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentToGetAppoinmentDtoMapper {

    private final ServiceToGetServiceDtoMapper serviceToGetServiceDtoMapper;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;

    public GetAppointmentDto map(Appointment appointment){
        return GetAppointmentDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentDate(appointment.getAppointmentDate())
                .createdAt(appointment.getCreatedAt())
                .modifiedOn(appointment.getModifiedOn())
                .status(appointment.getStatus())

                .service(serviceToGetServiceDtoMapper.map(appointment.getService()))
                .client(userToGetUserDtoMapper.map(appointment.getClient()))
                .employee(userToGetUserDtoMapper.map(appointment.getEmployee()))
                .build();
    }
 }
