package com.example.Reservation_app.Appointments;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/Appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

//    void xd(){appointmentRepository.findById()}

}
