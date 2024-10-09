package com.example.Reservation_app.Reviews.Review.dto;


import jakarta.validation.constraints.*;

public class AddReviewCommand {
        @NotEmpty
        String reviewContent;
        @NotNull
        @Positive
        @Max(5)
        @Min(1)
        Integer rating;
}




