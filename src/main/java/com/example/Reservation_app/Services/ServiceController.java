package com.example.Reservation_app.Services;

import com.example.Reservation_app.Services.command.AddServiceCommand;
import com.example.Reservation_app.Services.command.PatchServiceCommand;
import com.example.Reservation_app.Services.dto.PatchServiceResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    //TODO tu zrobić paginację
    @GetMapping("/all")
    List<Service> getAllServices(){
        return serviceService.findAll();
    }

    @GetMapping("/by_id/{serviceId}")
    Service getServiceByID(@PathVariable Long serviceId){
        return serviceService.findById(serviceId);
    }

    @GetMapping("/by_name/{name}")
    Service getServiceByName(@PathVariable String name){
        return serviceService.findByName(name);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    void addService(@RequestBody @Valid AddServiceCommand command){
        serviceService.addNewService(command);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/patch")
    ResponseEntity<PatchServiceResponseDto> patchService(@RequestBody @Valid PatchServiceCommand command){
        return ResponseEntity.ok(serviceService.patchService(command));
    }




//    //TODO ZASTANOWIC SIE CZY TO JEST POTRZEBNE ---- nie do wypierdolenia
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/delete")
//    void deleteService(@RequestParam Long serviceId){
//        serviceService.delete(serviceId);
//    }

}
