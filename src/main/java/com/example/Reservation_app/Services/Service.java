package com.example.Reservation_app.Services;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long service_id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    @Positive
    private Integer duration_minutes;
    @NotNull
    @Positive
    private Integer price;
    private LocalDateTime created_at;

    // mappedBy - indicates var in owning side of rel, in which corresponding services are stored ~ Non-Owning Side: This is the side that uses the mappedBy attribute to refer to the owning side.
    @OneToMany(mappedBy = "service")
    @JsonManagedReference
    private List<Appointment> appointment;
}
