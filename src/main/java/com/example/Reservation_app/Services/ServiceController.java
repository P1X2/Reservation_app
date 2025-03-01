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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/all")
    Page<GetServiceDto> getAllServices(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "2") Integer pageSize,
                                       @RequestParam(defaultValue = "createdAt") String sortBy,
                                       @RequestParam(defaultValue = "desc") String sortDir){
        return serviceService.findAll(page, pageSize, sortBy, sortDir);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/by-id/{serviceId}")
    GetServiceDto getServiceByID(@PathVariable Long serviceId){
        return serviceService.findById(serviceId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/by-name/{name}")
    GetServiceDto getServiceByName(@PathVariable String name){
        return serviceService.findByName(name);
    }

    @PreAuthorize("hasAuthority('ROLE_PRESIDENT')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    void addService(@RequestBody @Valid AddServiceCommand command){
        serviceService.addNewService(command);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/patch")
    ResponseEntity<PatchServiceResponseDto> patchService(@RequestBody @Valid PatchServiceCommand command){
        return ResponseEntity.ok(serviceService.patchService(command));
    }

}
