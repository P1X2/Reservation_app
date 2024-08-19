package com.example.Reservation_app.Appointments.Appointment;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Services.Service;
import com.example.Reservation_app.Users.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointment_id;

    private LocalDateTime appointmeny_date;
    @NotEmpty
    private AppointmentStatus status;

    @ManyToOne(optional = false)
    // name = "xxx" - references table in DB, not var in service instance
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User employee;

    @OneToOne(optional = true, mappedBy = "appointment")
    private Review review;








}
