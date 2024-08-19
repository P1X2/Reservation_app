package com.example.Reservation_app.Reviews.Review;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReviewDTO(
        @NotEmpty
        String review_content,
        @NotNull
        @Positive
        Integer rating)
{}




