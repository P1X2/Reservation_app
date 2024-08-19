package com.example.Reservation_app.Reviews.Review;


import com.example.Reservation_app.Appointments.Appointment.Appointment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long review_id;

    @NotEmpty
    private String review_content;
    @NotEmpty
    private Integer rating;
    private LocalDateTime created_at;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;



}
