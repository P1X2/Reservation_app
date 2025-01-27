package com.example.Reservation_app.Utils;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateAppointmentStatusScheduler {

    private final AppointmentRepository appointmentRepository;

    @Scheduled(fixedRate = 20000)
    public void updateStatusToCompleted(){
        List<Appointment> toUpdate = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getStatus().equals(AppointmentStatus.APPOINTMENT_CONFIRMED))
                .filter(appointment -> appointment.getAppointmentDate().isBefore(LocalDateTime.now()))
                .toList();

        toUpdate.forEach(
                appointment -> {
                    appointment.setStatus(AppointmentStatus.COMPLETED);
                    appointmentRepository.save(appointment);
                }
        );
        log.info("Appointment statuses updated from CONFIRMED to COMPLETED");
    }
}
