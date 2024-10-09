package com.example.Reservation_app.Services;

import com.example.Reservation_app.Services.dto.AddServiceCommand;
import com.example.Reservation_app.Services.dto.PatchServiceCommand;
import com.example.Reservation_app.Services.dto.PatchServiceResponseDto;
import com.example.Reservation_app.Services.mapper.AddServiceCommandToServiceMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final AddServiceCommandToServiceMapper addServiceCommandToServiceMapper;

    public Service findById(Long serviceId){
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Service findByName(String name){
        String capitalized = StringUtils.capitalize(name);
        return serviceRepository.findByName(capitalized)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    List<Service> findAll(){
        return serviceRepository.findAll();
    }


    public void addNewService(AddServiceCommand command){

        Service service = addServiceCommandToServiceMapper.map(command);

        serviceRepository.save(service);
    }

    public PatchServiceResponseDto patchService(PatchServiceCommand command){
        Service service = findById(command.getServiceId());

        Optional.ofNullable(command.getName()).ifPresent(service::setName);
        Optional.ofNullable(command.getDescription()).ifPresent(service::setDescription);
        Optional.ofNullable(command.getDurationMinutes()).ifPresent(service::setDurationMinutes);
        Optional.ofNullable(command.getPrice()).ifPresent(service::setPrice);

        service.setModifiedOn(LocalDateTime.now());

        serviceRepository.save(service);

        return PatchServiceResponseDto.builder()
                .name(service.getName())
                .description(service.getDescription())
                .durationMinutes(service.getDurationMinutes())
                .price(service.getPrice())
                .modifiedOn(service.getModifiedOn())
                .build();
}

//        serviceRepository.deleteById(serviceId);
//    }

}
