package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Services.ServiceRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;

    Page<Review> getByUserID(Long userID, Integer page, Integer pageSize, String sortBy, String sortDir){

        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        Pageable metadata = PageRequest.of(page, pageSize, sort);
        return reviewRepository.findByUsername(userID, metadata);
    }

    Page<Review> getByServiceID(Long serviceID, Integer page, Integer pageSize, String sortBy, String sortDir){

        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        Pageable metadata = PageRequest.of(page, pageSize, sort);
        return reviewRepository.findByService(serviceID, metadata);
    }

    //TODO MOZE JAKAS LOGIKA SPRAWDZAJĄCA CZY ZIUT BYL NA APP W PRZECG OSTATNIEGO TYG??
    void addReview(Long appointment_id, ReviewDTO reviewDTO){

        Optional<Appointment> appointmentRecord = appointmentRepository.findById(appointment_id);
        if (appointmentRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such appointment in database");
        }

        Review newReview = new Review();
        newReview.setReview_content(reviewDTO.review_content());
        newReview.setRating(reviewDTO.rating());
        newReview.setCreated_at(LocalDateTime.now());
        newReview.setAppointment(appointmentRecord.get());

        reviewRepository.save(newReview);

    }

    void updateReview(Long id, ReviewDTO reviewDTO){

        Optional<Review> reviewRecord = reviewRepository.findById(id);
        if(reviewRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Review updatedReview = reviewRecord.get();
        updatedReview.setReview_content(reviewDTO.review_content());
        updatedReview.setRating(reviewDTO.rating());

        reviewRepository.save(updatedReview);

    }

    void deleteComment(Long id){
        reviewRepository.deleteById(id);
    }

}

