package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.command.CreateAppointmentCommand;
import com.example.Reservation_app.Appointments.Appointment.dto.CreateAppointmentResponseDto;
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE')")
    @GetMapping("/get-by-date")
    Page<GetAppointmentDto> getAppointmentsByDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    )
    {
        return appointmentService.getByDate(date, page, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/get-by-userId/{userId}")
    Page<GetAppointmentDto> getAppointmentsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    )
    {
        return appointmentService.getByUserId(userId, page, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CreateAppointmentResponseDto> createNewAppointment(@Valid @RequestBody CreateAppointmentCommand createAppointmentCommand)
    {
       return ResponseEntity.ok(appointmentService.create(createAppointmentCommand));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE')")
    @PatchMapping("/update-status/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    void updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam AppointmentStatus newStatus
            )
    {
        appointmentService.updateStatus(appointmentId, newStatus);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE')")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    void deleteAppointment(@RequestParam Long appointmentId)
    {
        appointmentService.delete(appointmentId);
    }

}
