package com.example.Reservation_app.Reviews;


import com.example.Reservation_app.Appointments.Appointment.Appointment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;

    @OneToOne(optional = true)
    private Appointment appointment;



}
