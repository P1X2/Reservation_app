package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.command.CreateAppointmentCommand;
import com.example.Reservation_app.Appointments.Appointment.dto.CreateAppointmentResponseDto;
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Appointments.Appointment.mapper.AppointmentToCreateAppointmentResponseDtoMapper;
import com.example.Reservation_app.Appointments.Appointment.mapper.AppointmentToGetAppoinmentDtoMapper;
import com.example.Reservation_app.Appointments.Appointment.mapper.CreateAppointmentCommandToAppointmentDtoMapper;
import com.example.Reservation_app.Reviews.ReviewRepository;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.UserRepository;
import com.example.Reservation_app.Utils.ReservationAppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AppointmentToGetAppoinmentDtoMapper appointmentToGetAppoinmentDtoMapper;
    private final CreateAppointmentCommandToAppointmentDtoMapper createAppointmentCommandToAppointmentDtoMapper;
    private final AppointmentToCreateAppointmentResponseDtoMapper createAppointmentResponseDtoMapper;


    public Page<GetAppointmentDto> getByDate(LocalDate date, Integer page, Integer pageSize, String sortBy, String sortDir)
    {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return appointmentRepository.findAllByDate(start, end, ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir))
                .map(appointmentToGetAppoinmentDtoMapper::map);
    }

    public Page<GetAppointmentDto> getByUserId(Long userId, Integer page, Integer pageSize, String sortBy, String sortDir)
    {
        User client = userRepository.findById(userId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return appointmentRepository.findByClient(client, ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir))
                .map(appointmentToGetAppoinmentDtoMapper::map);
    }

    public CreateAppointmentResponseDto create(CreateAppointmentCommand createAppointmentCommand){
        Appointment appointment = createAppointmentCommandToAppointmentDtoMapper.map(createAppointmentCommand);
        appointmentRepository.save(appointment);

        return createAppointmentResponseDtoMapper.map(appointment);

    }

    void updateStatus(Long appointmentId, AppointmentStatus newStatus)
    {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);

    }

    public void delete(Long appointmentId)
    {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        appointmentRepository.findReviewIdToDelete(appointment.getAppointmentId()).ifPresent(reviewRepository::deleteById);
        appointmentRepository.delete(appointment);
    }

    public Appointment getAppointmentById(Long appointmentId){
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
