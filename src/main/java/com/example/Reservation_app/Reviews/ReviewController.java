package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Reviews.Review.Review;
import com.example.Reservation_app.Reviews.Review.dto.AddReviewCommand;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//TODO MOZE JAKAS LOGIKA SPRAWDZAJĄCA CZY ZIUT BYL NA APP W PRZECG OSTATNIEGO TYG(add_review)??
// *dodawanie komentów do wizyt ktore sie wydarzyły (aapp.status check)



@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    // todo do not return entity xdd
    @GetMapping("/get_by_userId/{userId}")
    Page<Review> getsByUser(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "2") Integer pageSize,
                                   @RequestParam(defaultValue = "created_at") String sortBy,
                                   @RequestParam(defaultValue = "desc") String sortDir)

    {
        return reviewService.getByUserId(userId, page, pageSize, sortBy, sortDir);
    }
    // todo do not return entity xdd
    @GetMapping("/get_by_serviceId/{serviceId}")
    Page<Review> getByService(@PathVariable Long serviceId,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "2") Integer pageSize,
                                     @RequestParam(defaultValue = "created_at") String sortBy,
                                     @RequestParam(defaultValue = "desc") String sortDir)
    {
        return reviewService.getByServiceId(serviceId, page, pageSize, sortBy, sortDir);
    }


    @PostMapping("/add_comment_appointment")
    @ResponseStatus(HttpStatus.CREATED)
    void addNew(@RequestParam Long appointmentId, @RequestBody @Valid AddReviewCommand addReviewCommand)
    {
        reviewService.addReview(appointmentId, addReviewCommand);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateReview(@RequestParam Long reviewId, @RequestBody @Valid AddReviewCommand addReviewCommand)
    {
        reviewService.updateReview(reviewId, addReviewCommand);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam Long reviewId)
    {
        reviewService.deleteComment(reviewId);
    }


}
