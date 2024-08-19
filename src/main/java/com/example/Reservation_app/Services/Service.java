package com.example.Reservation_app.Services;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
