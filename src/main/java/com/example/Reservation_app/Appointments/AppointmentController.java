package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.AppointmentDTO;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
//todo endpoint do oplacania dla usera
// end dla pracownikow do potwierdzania
// WSPOLNY END  DO CANCELOWANIA
// AUTOMATYCZNY SERVICE DO ZMIANY STANU APP JESLI DATA W DB < NOW()
// get all app by user_id

public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/get_appointment_for_date")
    List<Appointment> getAppointmentsByDate(@RequestParam LocalDate date){

        return appointmentService.getAppointmentsByDate(date);
    }

    @PostMapping("/create_new_appointment")
    @ResponseStatus(HttpStatus.CREATED)
    void createNewAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO)
    {
        appointmentService.addNewAppointment(appointmentDTO);
    }

    @PutMapping("/update_appointment_status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAppointmentStatus(
            @RequestParam Long appointmentId,
            @RequestParam AppointmentStatus newStatus
            )
    {
        appointmentService.updateAppointmentStatus(appointmentId, newStatus);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAppointment(@RequestParam Long appointmentId)
    {
        appointmentService.deleteAppointment(appointmentId);
    }

}
