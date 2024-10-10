package com.example.Reservation_app.Reviews.Review.mapper;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.dto.PatchReviewResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewToPatchReviewResponseDtoMapper {

    public PatchReviewResponseDto map(Review review){
        return PatchReviewResponseDto.builder()
                .reviewContent(review.getReviewContent())
                .rating(review.getRating())
                .modifiedOn(review.getModifiedOn())
                .appointmentId(review.getAppointment().getAppointment_id())
                .build();
    }
}
