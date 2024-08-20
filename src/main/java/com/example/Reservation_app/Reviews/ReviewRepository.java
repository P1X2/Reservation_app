package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Reviews.Review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT R.REVIEW_ID, R.RATING, R.REVIEW_CONTENT, R.CREATED_AT , R.APPOINTMENT_ID AS REVIEW_APP_ID, A.APPOINTMENT_ID AS APPOINTMENT_ID, A.SERVICE_ID AS APP_SER_ID, S.SERVICE_ID AS SERVICE_ID " +
            "FROM REVIEW R " +
            "JOIN APPOINTMENT A ON R.APPOINTMENT_ID = A.APPOINTMENT_ID " +
            "JOIN SERVICE S ON A.SERVICE_ID = S.SERVICE_ID " +
            "WHERE S.SERVICE_ID = :serviceID", nativeQuery = true)
    Page<Review> findByService(@Param("serviceID") Long serviceID, Pageable metadata);


    @Query(value = "SELECT R.REVIEW_ID, R.RATING, R.REVIEW_CONTENT, R.CREATED_AT, R.APPOINTMENT_ID AS REVIEW_APP_ID, A.APPOINTMENT_ID AS APPOINTMENT_ID, A.CLIENT_ID AS APP_CLI_ID, U.USER_ID AS USER_ID " +
            "FROM REVIEW R " +
            "JOIN APPOINTMENT A ON R.APPOINTMENT_ID = A.APPOINTMENT_ID " +
            "JOIN USERS U ON A.CLIENT_ID = U.USER_ID " +
            "WHERE U.USER_ID = :userID", nativeQuery = true)
    Page<Review> findByUsername(@Param("userID") Long userID, Pageable metadata);

}
