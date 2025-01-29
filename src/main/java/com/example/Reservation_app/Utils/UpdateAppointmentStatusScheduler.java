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

    @Scheduled(fixedDelay = 30000)
    public void updateStatusToCompleted(){
        List<Appointment> toUpdate = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getStatus().equals(AppointmentStatus.APPOINTMENT_CONFIRMED))
                .filter(appointment -> appointment.getAppointmentDate().isBefore(LocalDateTime.now()))
                .toList();

        toUpdate.forEach(
                appointment -> {
                    appointment.setStatus(AppointmentStatus.COMPLETED);
                }
        );
        appointmentRepository.saveAll(toUpdate);
        log.info("Appointment statuses updated from CONFIRMED to COMPLETED");
    }


    @Scheduled(fixedDelay = 30000)
    public void updateStatusToCancelled(){
        List<Appointment> toUpdate = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING_PAYMENT))
                .filter(appointment -> appointment.getAppointmentDate().isBefore(LocalDateTime.now()))
                .toList();

        toUpdate.forEach(
                appointment -> {
                    appointment.setStatus(AppointmentStatus.CANCELLED);
                }
        );
        appointmentRepository.saveAll(toUpdate);
        log.info("Not payed appointment statuses updated from PENDING_PAYMENT to CANCELLED");
    }
}
