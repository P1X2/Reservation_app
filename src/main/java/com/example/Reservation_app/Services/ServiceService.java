package com.example.Reservation_app.Services;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    Optional<com.example.Reservation_app.Services.Service> findByID(Long id){
        Optional<com.example.Reservation_app.Services.Service> serviceRecord = serviceRepository.findById(id);
        if (serviceRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reason: reason");
        }
        return serviceRecord;
    }

    Optional<com.example.Reservation_app.Services.Service> findByName(String name){
        String capitalized = StringUtils.capitalize(name);
        Optional<com.example.Reservation_app.Services.Service> serviceRecord = serviceRepository.findByName(capitalized);
        if (serviceRecord.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return serviceRecord;
    }

    List<com.example.Reservation_app.Services.Service> findAll(){
        return  serviceRepository.findAll();
    }

    void save(com.example.Reservation_app.Services.Service service){
        service.setCreated_at(LocalDateTime.now());
        serviceRepository.save(service);
    }

    void updatePrice(Long id, Integer newPrice){

        Optional<com.example.Reservation_app.Services.Service> serviceRecord = this.findByID(id);
        if(serviceRecord.isEmpty()){throw new ResponseStatusException(HttpStatus.NOT_FOUND);}

        com.example.Reservation_app.Services.Service updatedService = serviceRecord.get();
        updatedService.setPrice(newPrice);
        serviceRepository.save(updatedService);
    }

    void delete(Long id){
        serviceRepository.deleteById(id);
    }

}
