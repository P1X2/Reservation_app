package com.example.Reservation_app.Appointments.Appointment;

import com.example.Reservation_app.Reviews.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = true, mappedBy = "appointment")
    private Review review;








}
