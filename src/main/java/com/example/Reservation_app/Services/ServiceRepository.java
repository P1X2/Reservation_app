package com.example.Reservation_app.Services;

import com.example.Reservation_app.Services.Service.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);
}
