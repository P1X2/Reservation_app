package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus;
import com.example.Reservation_app.Reviews.ReviewRepository;
import com.example.Reservation_app.Services.ServiceRepository;
import com.example.Reservation_app.Services.Service;
import com.example.Reservation_app.Users.User.User;
import com.example.Reservation_app.Users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final ReviewRepository reviewRepository;


    Page<Appointment> getByDate(LocalDate date, Integer page, Integer pageSize, String sortBy, String sortDir)
    {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        Pageable metadata = PageRequest.of(page, pageSize, sort);

        return appointmentRepository.findAllByDate(start, end, metadata);
    }

    //TODO TEST
    Page<Appointment> getByUserId(Long userId, Integer page, Integer pageSize, String sortBy, String sortDir)
    {
        User client = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        Pageable metadata = PageRequest.of(page, pageSize, sort);

        return appointmentRepository.findByClient(client, metadata);
    }


    void addNew(GetAppointmentDto getAppointmentDto){

        Optional<User> client = userRepository.findById(getAppointmentDto.clientId());
        Optional<User> employee = userRepository.findById(getAppointmentDto.employeeId());
        Optional<Service> service = serviceRepository.findById(getAppointmentDto.serviceId());

        if (client.isEmpty() || employee.isEmpty() || service.isEmpty()){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid clientId, employeeID or serviceId");

        }

        Appointment newAppointment = new Appointment();
        newAppointment.setService(service.get());
        newAppointment.setClient(client.get());
        newAppointment.setEmployee(employee.get());
        newAppointment.setAppointment_date(getAppointmentDto.appointmentDate());
        newAppointment.setStatus(AppointmentStatus.PENDING_PAYMENT);

        appointmentRepository.save(newAppointment);

    }

    void updateStatus(Long appointmentId, AppointmentStatus newStatus)
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

    public void delete(Long appointmentID)
    {
        Optional<Appointment> appointmentRecord = appointmentRepository.findById(appointmentID);
        if (appointmentRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Appointment appointment = appointmentRecord.get();
        Optional<Long> bandedReview = appointmentRepository.findReviewsToDelete(appointment.getAppointment_id());

        if(bandedReview.isPresent()){
            reviewRepository.deleteById(bandedReview.get());
        }

        appointmentRepository.delete(appointment);
    }

    public Appointment getAppointmentById(Long appointmentId){
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
