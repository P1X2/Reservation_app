package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Reviews.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Appointments {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = true, mappedBy = "appointment")
    private Review review;








}
