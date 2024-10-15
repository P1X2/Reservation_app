package com.example.Reservation_app.Services.Service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    private String name;
    private String description;
    private Integer durationMinutes;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedOn;

}
