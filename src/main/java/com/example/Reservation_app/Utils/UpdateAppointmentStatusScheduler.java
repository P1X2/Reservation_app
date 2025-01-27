package com.example.Reservation_app.Utils;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UpdateAppointmentStatusScheduler {

    private final AppointmentRepository appointmentRepository;

    @Scheduled(fixedRate = 20, timeUnit = TimeUnit.SECONDS)
    private void updateStatusToCompleted(){
        List<Appointment> toUpdate = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getStatus().equals(AppointmentStatus.DONE_PAYMENT))
                .filter(appointment -> appointment.getAppointmentDate().isBefore(LocalDateTime.now()))
                .toList();

        toUpdate.forEach(
                appointment -> {
                    appointment.setStatus(AppointmentStatus.COMPLETED);
                    appointmentRepository.save(appointment);
                }
        );

    }
}
