package com.example.Reservation_app.Services;

import com.example.Reservation_app.Services.Service.command.AddServiceCommand;
import com.example.Reservation_app.Services.Service.command.PatchServiceCommand;
import com.example.Reservation_app.Services.Service.dto.GetServiceDto;
import com.example.Reservation_app.Services.Service.dto.PatchServiceResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    //TODO tu zrobić paginację
    @GetMapping("/all")
    Page<GetServiceDto> getAllServices(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "2") Integer pageSize,
                                       @RequestParam(defaultValue = "createdAt") String sortBy,
                                       @RequestParam(defaultValue = "desc") String sortDir){
        return serviceService.findAll(page, pageSize, sortBy, sortDir);
    }

    @GetMapping("/by-id/{serviceId}")
    GetServiceDto getServiceByID(@PathVariable Long serviceId){
        return serviceService.findById(serviceId);
    }

    @GetMapping("/by-name/{name}")
    GetServiceDto getServiceByName(@PathVariable String name){
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
