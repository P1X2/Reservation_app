package com.example.Reservation_app.Services;

import com.example.Reservation_app.Services.Service.Service;
import com.example.Reservation_app.Services.Service.command.AddServiceCommand;
import com.example.Reservation_app.Services.Service.command.PatchServiceCommand;
import com.example.Reservation_app.Services.Service.dto.GetServiceDto;
import com.example.Reservation_app.Services.Service.dto.PatchServiceResponseDto;
import com.example.Reservation_app.Services.Service.mapper.AddServiceCommandToServiceMapper;
import com.example.Reservation_app.Services.Service.mapper.ServiceToGetServiceDtoMapper;
import com.example.Reservation_app.Services.Service.mapper.ServiceToPatchServiceResponseDto;
import com.example.Reservation_app.Utils.ReservationAppUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final AddServiceCommandToServiceMapper addServiceCommandToServiceMapper;
    private final ServiceToGetServiceDtoMapper serviceToGetServiceDtoMapper;
    private final ServiceToPatchServiceResponseDto serviceToPatchServiceResponseDto;

    public GetServiceDto findById(Long serviceId){
        return serviceRepository.findById(serviceId)
                .map(serviceToGetServiceDtoMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public GetServiceDto findByName(String name){
        String capitalized = StringUtils.capitalize(name);
        return serviceRepository.findByName(capitalized)
                .map(serviceToGetServiceDtoMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    Page<GetServiceDto> findAll(Integer page, Integer pageSize, String sortBy, String sortDir){
        return serviceRepository.findAll(ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir))
                .map(serviceToGetServiceDtoMapper::map);
    }

    public void addNewService(AddServiceCommand command){

        Service service = addServiceCommandToServiceMapper.map(command);
        serviceRepository.save(service);
    }

    public PatchServiceResponseDto patchService(PatchServiceCommand command) {
        Service service = serviceRepository.findById(command.getServiceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional.ofNullable(command.getName()).ifPresent(service::setName);
        Optional.ofNullable(command.getDescription()).ifPresent(service::setDescription);
        Optional.ofNullable(command.getDurationMinutes()).ifPresent(service::setDurationMinutes);
        Optional.ofNullable(command.getPrice()).ifPresent(service::setPrice);

        service.setModifiedOn(LocalDateTime.now());

        serviceRepository.save(service);

        return serviceToPatchServiceResponseDto.map(service);
    }

}
