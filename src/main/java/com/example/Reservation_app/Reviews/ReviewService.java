package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.ReviewDTO;
import lombok.AllArgsConstructor;
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

//        Sort sort = Sort.by(sortBy);
//        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
//        Pageable metadata = PageRequest.of(page, pageSize, sort);

        Pageable metadata = PageRequest.of(page, pageSize);

        return reviewRepository.findByUsername(userID, metadata);
    }

    Page<Review> getByServiceID(Long serviceID, Integer page, Integer pageSize, String sortBy, String sortDir){
//
//        Sort sort = Sort.by(sortBy);
//        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
//        Pageable metadata = PageRequest.of(page, pageSize, sort);

        Pageable metadata = PageRequest.of(page, pageSize);

        return reviewRepository.findByService(serviceID, metadata);
    }

    void addReview(Long appointmentID, ReviewDTO reviewDTO){

        Optional<Appointment> appointmentRecord = appointmentRepository.findById(appointmentID);
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

    void updateReview(Long reviewID, ReviewDTO reviewDTO){

        Optional<Review> reviewRecord = reviewRepository.findById(reviewID);
        if(reviewRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Review updatedReview = reviewRecord.get();
        updatedReview.setReview_content(reviewDTO.review_content());
        updatedReview.setRating(reviewDTO.rating());

        reviewRepository.save(updatedReview);

    }

    void deleteComment(Long reviewID){

        Optional<Review> reviewRecord = reviewRepository.findById(reviewID);
        if(reviewRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        reviewRepository.delete(reviewRecord.get());
    }

}

