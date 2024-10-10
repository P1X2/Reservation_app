package com.example.Reservation_app.Services.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
