package com.example.Reservation_app.Services.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddServiceCommand {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private Integer durationMinutes;
    @NotNull
    private Integer price;
}
