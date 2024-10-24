package com.example.Reservation_app.Appointments.Appointment;


import com.example.Reservation_app.Services.Service.Service;
import com.example.Reservation_app.Users.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    private LocalDateTime appointmentDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedOn;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(optional = false)
    // name = "xxx" - references table in DB, not var in service instance (fk's in appointment table)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

//    @OneToOne(optional = true, mappedBy = "appointment", cascade = CascadeType.ALL)
//    private Review review;

}
