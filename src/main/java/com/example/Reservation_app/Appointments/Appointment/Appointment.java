package com.example.Reservation_app.Appointments.Appointment;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Services.Service;
import com.example.Reservation_app.Users.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointment_id;

    private LocalDateTime appointment_date;
    @Enumerated(EnumType.STRING) // defaultowo zaciąga do ordinala (int)
    private AppointmentStatus status;

    @ManyToOne(optional = false)
    // name = "xxx" - references table in DB, not var in service instance (fk's in appointment table)
    @JoinColumn(name = "service_id", nullable = false)
    @JsonBackReference
    private Service service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;


    @OneToOne(optional = true, mappedBy = "appointment", cascade = CascadeType.ALL)
    private Review review;

}
