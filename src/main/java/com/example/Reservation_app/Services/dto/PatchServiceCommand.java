package com.example.Reservation_app.Services.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatchServiceCommand {
    @NotNull
    private Long serviceId;

    private String name;
    private String description;
    private Integer durationMinutes;
    private Integer price;

}
