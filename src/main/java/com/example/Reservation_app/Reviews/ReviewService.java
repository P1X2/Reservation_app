package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Appointments.AppointmentService;
import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.command.AddReviewCommand;
import com.example.Reservation_app.Reviews.Review.dto.GetReviewDto;
import com.example.Reservation_app.Reviews.Review.mapper.AddReviewCommandToReviewMapper;
import com.example.Reservation_app.Reviews.Review.mapper.ReviewToGetReviewDtoMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentService appointmentService;
    private final AddReviewCommandToReviewMapper addReviewCommandToReviewMapper;
    private final ReviewToGetReviewDtoMapper reviewToGetReviewDtoMapper;

    Page<GetReviewDto> getByUserId(Long userId, Integer page, Integer pageSize, String sortBy, String sortDir){
        return reviewRepository.findByUsername(userId, getPageMetadata(page, pageSize, sortBy, sortDir))
                .map(reviewToGetReviewDtoMapper::map);
    }

    Page<GetReviewDto> getByServiceId(Long serviceId, Integer page, Integer pageSize, String sortBy, String sortDir){
        return reviewRepository.findByService(serviceId, getPageMetadata(page, pageSize, sortBy, sortDir))
                .map(reviewToGetReviewDtoMapper::map);
        // todo dodac mapping na dto
    }

    private Pageable getPageMetadata( Integer page, Integer pageSize, String sortBy, String sortDir){
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        return  PageRequest.of(page, pageSize, sort);
    }

    void addReview(Long appointmentId, AddReviewCommand addReviewCommand){

        Review newReview = addReviewCommandToReviewMapper.map(addReviewCommand);
        newReview.setAppointment(appointmentService.getAppointmentById(appointmentId));

        reviewRepository.save(newReview);
    }

    void patchReview(Long reviewId, AddReviewCommand addReviewCommand){
        Review reviewRecord = getByReviewId(reviewId);

        Optional.ofNullable(addReviewCommand.getReviewContent()).ifPresent(reviewRecord::setReviewContent);
        reviewRecord.setRating(addReviewCommand.getRating());
        reviewRecord.setModifiedOn(LocalDateTime.now());

        reviewRepository.save(reviewRecord);
    }

    void deleteComment(Long reviewId){
        reviewRepository.delete(getByReviewId(reviewId));
    }

    private Review getByReviewId(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}

