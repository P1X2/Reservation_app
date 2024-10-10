package com.example.Reservation_app.Services.Service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetServiceDto {
    private Long serviceId;

    private String name;
    private String description;
    private Integer durationMinutes;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedOn;
}
