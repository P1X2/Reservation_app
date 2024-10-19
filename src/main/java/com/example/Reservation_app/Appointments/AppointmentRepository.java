package com.example.Reservation_app.Appointments;

import com.example.Reservation_app.Appointments.Appointment.Appointment;
import com.example.Reservation_app.Users.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * " +
            "FROM APPOINTMENTS " +
            "WHERE (APPOINTMENT_DATE BETWEEN :START AND :END)",
            nativeQuery = true)
    Page<Appointment> findAllByDate(@Param("START") LocalDateTime start, @Param("END") LocalDateTime end, Pageable metadata);

    @Query(value = "SELECT R.REVIEW_ID " +
            "FROM APPOINTMENTS A " +
            "JOIN REVIEWS R USING (APPOINTMENT_ID) " +
            "WHERE (A.APPOINTMENT_ID = :appointmentId)", nativeQuery = true)
    Optional<Long> findReviewIdToDelete(@Param("appointmentId") Long appointmentId);

    // delete user method
    List<Appointment> findByClient(User client);
    // find app by client method
    Page<Appointment> findByClient(User client, Pageable metadata);
}
