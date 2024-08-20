package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * " +
            "FROM APPOINTMENT " +
            "WHERE (APPOINTMENT_DATE BETWEEN :START AND :END )",
            nativeQuery = true)
    List<Appointment> findAllByDate(@Param("START") LocalDateTime start,@Param("END") LocalDateTime end);

}
