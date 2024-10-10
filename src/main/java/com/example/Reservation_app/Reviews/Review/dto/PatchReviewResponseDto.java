package com.example.Reservation_app.Reviews.Review.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatchReviewResponseDto {

    private String reviewContent;
    private Integer rating;
    private LocalDateTime modifiedOn;

    private Long appointmentId;
}
