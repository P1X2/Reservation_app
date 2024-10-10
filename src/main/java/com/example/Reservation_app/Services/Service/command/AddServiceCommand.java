package com.example.Reservation_app.Services.Service.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
