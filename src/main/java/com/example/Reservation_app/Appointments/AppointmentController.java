package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.AppointmentDTO;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//todo endpoint do oplacania dla usera
// end dla pracownikow do potwierdzania
// WSPOLNY END  DO CANCELOWANIA
// AUTOMATYCZNY SERVICE DO ZMIANY STANU APP JESLI DATA W DB < NOW()
// get all app by user_id

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/get_for_date/{date}")
    Page<Appointment> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "appointment_date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    )
    {
        return appointmentService.getByDate(date, page, pageSize, sortBy, sortDir);
    }

    @GetMapping("/get_by_userId")
    Page<Appointment> getAppointmentsByUserId(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "appointment_date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    )
    {
        return appointmentService.getByUserId(userId, page, pageSize, sortBy, sortDir);
    }




    @PostMapping("/create_new")
    @ResponseStatus(HttpStatus.CREATED)
    void createNewAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO)
    {
        appointmentService.addNew(appointmentDTO);
    }

    @PutMapping("/update_status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAppointmentStatus(
            @RequestParam Long appointmentId,
            @RequestParam AppointmentStatus newStatus
            )
    {
        appointmentService.updateStatus(appointmentId, newStatus);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAppointment(@RequestParam Long appointmentId)
    {
        appointmentService.delete(appointmentId);
    }

}
