package com.example.Reservation_app.Reviews.Review.dto;

import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
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

    private GetAppointmentDto appointment;
}
