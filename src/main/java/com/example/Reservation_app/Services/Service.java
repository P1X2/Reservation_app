package com.example.Reservation_app.Services;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Integer duration_minutes;
    private Integer price;
    private LocalDateTime created_at;
}
