package com.example.Reservation_app.Services.Service.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchServiceCommand {
    @NotNull
    private Long serviceId;

    private String name;
    private String description;
    private Integer durationMinutes;
    private Integer price;

}
