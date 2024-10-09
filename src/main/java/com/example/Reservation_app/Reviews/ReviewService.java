package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.AppointmentRepository;
import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.dto.AddReviewCommand;
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

    Page<Review> getByUserId(Long userId, Integer page, Integer pageSize, String sortBy, String sortDir){
        return reviewRepository.findByUsername(userId, getPageMetadata(page, pageSize, sortBy, sortDir));
    }

    Page<Review> getByServiceId(Long serviceId, Integer page, Integer pageSize, String sortBy, String sortDir){
        Page<Review> reviewsPage = reviewRepository.findByService(serviceId, getPageMetadata(page, pageSize, sortBy, sortDir));
        // todo dodac mapping na dto
    }

    private Pageable getPageMetadata( Integer page, Integer pageSize, String sortBy, String sortDir){
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        return  PageRequest.of(page, pageSize, sort);
    }

    void addReview(Long appointmentId, AddReviewCommand addReviewCommand){

        Optional<Appointment> appointmentRecord = appointmentRepository.findById(appointmentId);
        if (appointmentRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such appointment in database");
        }

        Review newReview = new Review();
        newReview.setReview_content(addReviewCommand.review_content());
        newReview.setRating(addReviewCommand.rating());
        newReview.setCreated_at(LocalDateTime.now());
        newReview.setAppointment(appointmentRecord.get());

        reviewRepository.save(newReview);

    }

    void updateReview(Long reviewId, AddReviewCommand addReviewCommand){

        Optional<Review> reviewRecord = reviewRepository.findById(reviewId);
        if(reviewRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Review updatedReview = reviewRecord.get();
        updatedReview.setReview_content(addReviewCommand.review_content());
        updatedReview.setRating(addReviewCommand.rating());

        reviewRepository.save(updatedReview);

    }

    void deleteComment(Long reviewId){

        Optional<Review> reviewRecord = reviewRepository.findById(reviewId);
        if(reviewRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        reviewRepository.delete(reviewRecord.get());
    }

}

