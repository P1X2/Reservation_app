package com.example.Reservation_app.Reviews.Review.mapper;

import com.example.Reservation_app.Appointments.Appointment.mapper.AppointmentToGetAppoinmentDtoMapper;
import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.dto.PatchReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewToPatchReviewResponseDtoMapper {

    private final AppointmentToGetAppoinmentDtoMapper appointmentToGetAppoinmentDtoMapper;

    public PatchReviewResponseDto map(Review review){
        return PatchReviewResponseDto.builder()
                .reviewContent(review.getReviewContent())
                .rating(review.getRating())
                .modifiedOn(review.getModifiedOn())
                .appointment(appointmentToGetAppoinmentDtoMapper.map(review.getAppointment()))
                .build();
    }
}
