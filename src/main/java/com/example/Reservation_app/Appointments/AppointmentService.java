package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.AppointmentDTO;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.ReviewRepository;
import com.example.Reservation_app.Services.ServiceRepository;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final ReviewRepository reviewRepository;


    List<Appointment> getAppointmentsByDate(LocalDate date)
    {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return appointmentRepository.findAllByDate(start, end);
    }


    void addNewAppointment(AppointmentDTO appointmentDTO){

        Optional<User> client = userRepository.findById(appointmentDTO.clientId());
        Optional<User> employee = userRepository.findById(appointmentDTO.employeeId());
        Optional<com.example.Reservation_app.Services.Service> service = serviceRepository.findById(appointmentDTO.serviceId());

        if (client.isEmpty() || employee.isEmpty() || service.isEmpty()){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid clientId, employeeID or serviceId");

        }

        Appointment newAppointment = new Appointment();
        newAppointment.setService(service.get());
        newAppointment.setClient(client.get());
        newAppointment.setEmployee(employee.get());
        newAppointment.setAppointment_date(LocalDateTime.now());
        newAppointment.setStatus(AppointmentStatus.PENDING_PAYMENT);

        appointmentRepository.save(newAppointment);

    }

    void updateAppointmentStatus(Long appointmentId, AppointmentStatus newStatus)
    {
        Optional<Appointment> appointmentRecord = appointmentRepository.findById(appointmentId);

        if(appointmentRecord.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Appointment appointment = appointmentRecord.get();
        appointment.setStatus(newStatus);

        appointmentRepository.save(appointment);

    }

    void deleteAppointment(Long appointmentID)
    {
        Optional<Appointment> appointmentRecord = appointmentRepository.findById(appointmentID);
        if (appointmentRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Appointment appointment = appointmentRecord.get();
        Review bandedReview = appointment.getReview();

        reviewRepository.delete(bandedReview);
        appointmentRepository.delete(appointment);
    }


}
