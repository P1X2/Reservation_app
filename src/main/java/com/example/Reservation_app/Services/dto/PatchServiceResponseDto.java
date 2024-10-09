package com.example.Reservation_app.Services.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatchServiceResponseDto {
    private String name;
    private String description;
    private Integer durationMinutes;
    private Integer price;
    private LocalDateTime modifiedOn;
}
