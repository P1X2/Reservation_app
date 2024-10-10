package com.example.Reservation_app.Reviews.Review.mapper;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.command.AddReviewCommand;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AddReviewCommandToReviewMapper {

    public Review map(AddReviewCommand addReviewCommand){
        return Review.builder()
                .reviewContent(addReviewCommand.getReviewContent())
                .rating(addReviewCommand.getRating())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
