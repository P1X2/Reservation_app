package com.example.Reservation_app.Appointments.Appointment;

import com.example.Reservation_app.Reviews.Review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue
    private Long appointment_id;

    @OneToOne(optional = true, mappedBy = "appointment")
    private Review review;








}
