package com.example.Reservation_app.Services.mapper;


import com.example.Reservation_app.Services.Service;
import com.example.Reservation_app.Services.command.AddServiceCommand;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AddServiceCommandToServiceMapper {

    public Service map(AddServiceCommand command) {
        return Service.builder()
                .name(command.getName())
                .description(command.getDescription())
                .durationMinutes(command.getDurationMinutes())
                .price(command.getPrice())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
