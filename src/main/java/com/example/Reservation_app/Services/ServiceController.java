package com.example.Reservation_app.Services;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    //TODO tu zrobić paginację + @requsetParam
    @GetMapping("/all")
    List<com.example.Reservation_app.Services.Service> getAllServices(){
        return serviceService.findAll();
    }

    @GetMapping("/by_id/{id}")
    Optional<com.example.Reservation_app.Services.Service> getServiceByID(@PathVariable Long id){
        return serviceService.findByID(id);
    }

    @GetMapping("/by_name/{name}")
    Optional<com.example.Reservation_app.Services.Service> getServiceByName(@PathVariable String name){
        return serviceService.findByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add_service")
    void addService(@RequestBody @Valid Service service){
        serviceService.save(service);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update_service")
    void updateServicePrice(@RequestParam Long id, @RequestParam Integer newPrice){
        serviceService.updatePrice(id, newPrice);
    }
    //TODO ZASTANOWIC SIE CZY TO JEST POTRZEBNE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteService(@PathVariable Long id){
        serviceService.delete(id);
    }

}
