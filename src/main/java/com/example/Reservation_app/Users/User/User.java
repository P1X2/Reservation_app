package com.example.Reservation_app.Users.User;


import com.example.Reservation_app.Appointments.Appointment.Appointment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Email
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    @Enumerated(EnumType.STRING)
    private UserStatus user_status;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime created_At;

    @OneToMany(mappedBy = "employee")
    List<Appointment> employeeAppointment;

    @OneToMany(mappedBy = "client")
    List<Appointment> clientAppointment;



}
