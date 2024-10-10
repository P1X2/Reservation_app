package com.example.Reservation_app.Reviews.Review.mapper;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.dto.GetReviewDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ReviewToGetReviewDtoMapper {

    public GetReviewDto map(Review review){
        return GetReviewDto.builder()
                .reviewId(review.getReviewId())
                .reviewContent(review.getReviewContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .modifiedOn(review.getModifiedOn())
                .appointmentId(review.getAppointment().getAppointment_id())
                .build();
    }
}
