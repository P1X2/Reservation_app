package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.ReviewDTO;
import jakarta.validation.Valid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//TODO MOZE JAKAS LOGIKA SPRAWDZAJĄCA CZY ZIUT BYL NA APP W PRZECG OSTATNIEGO TYG(add_review)??
// *dodawanie komentów do wizyt ktore sie wydarzyły (aapp.status)
// *SPRAWDZIC CZY PAGINACJA DZIAŁA
// refactor znaw z Id + w txt!!
// *** pozmieniac querries w repozytorium (nazwy Id)

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get_by_userId/{userId}")
    Page<Review> getCommentsByUser(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "2") Integer pageSize,
                                   @RequestParam(defaultValue = "created_at") String sortBy,
                                   @RequestParam(defaultValue = "desc") String sortDir)

    {
        return reviewService.getByUserId(userId, page, pageSize, sortBy, sortDir);
    }

    @GetMapping("/get_by_service/{serviceId}")
    Page<Review> getCommentByService(@PathVariable Long serviceId,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "2") Integer pageSize,
                                     @RequestParam(defaultValue = "created_at") String sortBy,
                                     @RequestParam(defaultValue = "desc") String sortDir)
    {
        return reviewService.getByServiceId(serviceId, page, pageSize, sortBy, sortDir);
    }


    @PostMapping("/add_comment_appointment")
    @ResponseStatus(HttpStatus.CREATED)
    void addComment(@RequestParam Long appointmentId, @RequestBody @Valid ReviewDTO reviewDTO)
    {
        reviewService.addReview(appointmentId, reviewDTO);
    }

    @PutMapping("/update_review")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCommentRating(@RequestParam Long reviewId, @RequestBody @Valid ReviewDTO reviewDTO)
    {
        reviewService.updateReview(reviewId, reviewDTO);
    }

    @DeleteMapping("/delete_review")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@RequestParam Long reviewId)
    {
        reviewService.deleteComment(reviewId);
    }


}
