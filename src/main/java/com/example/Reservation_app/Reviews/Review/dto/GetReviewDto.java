package com.example.Reservation_app.Reviews.Review.dto;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetReviewDto {

    private Long reviewId;
    private String reviewContent;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedOn;

    private GetAppointmentDto appointment;
}
