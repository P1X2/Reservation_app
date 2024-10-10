package com.example.Reservation_app.Appointments.Appointment.mapper;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.dto.CreateAppointmentResponseDto;
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
import com.example.Reservation_app.Services.Service.mapper.ServiceToGetServiceDtoMapper;
import com.example.Reservation_app.Users.User.mapper.UserToGetUserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentToCreateAppointmentResponseDtoMapper {

    private final ServiceToGetServiceDtoMapper serviceToGetServiceDtoMapper;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;

    public CreateAppointmentResponseDto map(Appointment appointment){
        return CreateAppointmentResponseDto.builder()
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
