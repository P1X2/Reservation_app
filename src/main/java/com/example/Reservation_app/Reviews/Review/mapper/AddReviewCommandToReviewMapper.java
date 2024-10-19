package com.example.Reservation_app.Reviews.Review.mapper;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.command.AddReviewCommand;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AddReviewCommandToReviewMapper {

    public Review map(AddReviewCommand command){
        return Review.builder()
                .reviewContent(command.getReviewContent())
                .rating(command.getRating())
                .createdAt(LocalDateTime.now())
                .modifiedOn(LocalDateTime.now())
                .build();
    }
}
