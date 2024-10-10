package com.example.Reservation_app.Services.Service.mapper;

import com.example.Reservation_app.Services.Service.Service;
import com.example.Reservation_app.Services.Service.dto.GetServiceDto;
import org.springframework.stereotype.Component;

@Component
public class ServiceToGetServiceDtoMapper {

    public GetServiceDto map(Service service){
        return GetServiceDto.builder()
                .serviceId(service.getServiceId())
                .name(service.getName())
                .description(service.getDescription())
                .durationMinutes(service.getDurationMinutes())
                .price(service.getPrice())
                .createdAt(service.getCreatedAt())
                .modifiedOn(service.getModifiedOn())
                .build();
    }
}
