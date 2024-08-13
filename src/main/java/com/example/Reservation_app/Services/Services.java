package com.example.Reservation_app.Services;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Services {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer duration;
    private Integer price;
    private LocalDateTime created_at;
}
