package com.example.Reservation_app.Services.Service.mapper;

import com.example.Reservation_app.Services.Service.Service;
import com.example.Reservation_app.Services.Service.dto.PatchServiceResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ServiceToPatchServiceResponseDto {

    public PatchServiceResponseDto map (Service service){
        return PatchServiceResponseDto.builder()
                .name(service.getName())
                .description(service.getDescription())
                .durationMinutes(service.getDurationMinutes())
                .price(service.getPrice())
                .modifiedOn(service.getModifiedOn())
                .build();
    }

}
