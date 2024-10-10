package com.example.Reservation_app.Reviews.Review.command;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewCommand {
        String reviewContent;
        @NotNull
        @Max(5)
        @Min(1)
        Integer rating;
}




