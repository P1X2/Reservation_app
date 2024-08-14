package com.example.Reservation_app.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    @Autowired
    private final ServiceService serviceService;
    private final ServiceRepository serviceRepository;

    @GetMapping("/all")
    List<Service> fetchAllServices(){
        return serviceRepository.findAll();
    }

}
