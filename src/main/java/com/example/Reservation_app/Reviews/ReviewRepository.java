package com.example.Reservation_app.Reviews;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM REVIEW R " +
            "JOIN APPOINTMENT A ON (R.APPOINTMENT_ID = A.ID)" +
            "JOIN SERVICE S ON (A.SERVICE_ID = S.ID" +
            "WHERE (S.ID == :serviceID) )", nativeQuery = true)
    Page<Review> findByService(@Param("serviceId") Long serviceId ,Pageable metadata);

    @Query(value = "SELECT * FROM REVIEW R " +
            "JOIN APPOINTMENT A ON(R.APPOINTMENT_ID = A.ID)" +
            "JOIN USER U ON (A.CLIENT_ID = U.ID)" +
            "WHERE (U.ID == :userID)", nativeQuery = true)
    Page<Review> findByUsername(@Param("userID") Long userID, Pageable metadata);
}
