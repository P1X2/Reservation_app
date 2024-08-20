package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.ReviewDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//TODO MOZE JAKAS LOGIKA SPRAWDZAJĄCA CZY ZIUT BYL NA APP W PRZECG OSTATNIEGO TYG(add_review)??
// *dodawanie komentów do wizyt ktore sie wydarzyły (aapp.status)
// *SPRAWDZIC CZY PAGINACJA DZIAŁA
// refactor znaw z ID + w txt!!
// *** pozmieniac querries w repozytorium (nazwy id)

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get_by_userID/{userID}")
    Page<Review> getCommentsByUser(@PathVariable Long userID,
                                   @RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                   @RequestParam(defaultValue = "createdAt") String sortBy,
                                   @RequestParam(defaultValue = "desc") String sortDir)

    {
        return reviewService.getByUserID(userID, page, pageSize, sortBy, sortDir);
    }

    @GetMapping("/get_by_service/{service_id}")
    Page<Review> getCommentByService(@PathVariable Long serviceID,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "5") Integer pageSize,
                                     @RequestParam(defaultValue = "createdAt") String sortBy,
                                     @RequestParam(defaultValue = "desc") String sortDir)
    {
        return reviewService.getByServiceID(serviceID, page, pageSize, sortBy, sortDir);
    }


    @PostMapping("/add_comment_appointment")
    @ResponseStatus(HttpStatus.CREATED)
    void addComment(@RequestParam Long appointmentID, @RequestBody @Valid ReviewDTO reviewDTO)
    {
        reviewService.addReview(appointmentID, reviewDTO);
    }

    @PutMapping("/update_review")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCommentRating(@RequestParam Long reviewID, @RequestBody @Valid ReviewDTO reviewDTO)
    {
        reviewService.updateReview(reviewID, reviewDTO);
    }

    @DeleteMapping("/delete_review")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@RequestParam Long reviewID)
    {
        reviewService.deleteComment(reviewID);
    }


}
