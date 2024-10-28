package com.example.Reservation_app.Reviews;

import com.example.Reservation_app.Reviews.Review.command.AddReviewCommand;
import com.example.Reservation_app.Reviews.Review.dto.GetReviewDto;
import com.example.Reservation_app.Reviews.Review.dto.PatchReviewResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/get-by-userId/{userId}")
    Page<GetReviewDto> getsByUser(@PathVariable Long userId,
                                  @RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "2") Integer pageSize,
                                  @RequestParam(defaultValue = "created_at") String sortBy,
                                  @RequestParam(defaultValue = "desc") String sortDir)

    {
        return reviewService.getByUserId(userId, page, pageSize, sortBy, sortDir);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @GetMapping("/get-by-serviceId/{serviceId}")
    Page<GetReviewDto> getByService(@PathVariable Long serviceId,
                                    @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "2") Integer pageSize,
                                     @RequestParam(defaultValue = "created_at") String sortBy,
                                     @RequestParam(defaultValue = "desc") String sortDir)
    {
        return reviewService.getByServiceId(serviceId, page, pageSize, sortBy, sortDir);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_CLIENT')")
    @PostMapping("/add-review")
    @ResponseStatus(HttpStatus.OK)
    void addNew(@RequestParam Long appointmentId, @RequestBody @Valid AddReviewCommand addReviewCommand) {
        reviewService.addReview(appointmentId, addReviewCommand);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CLIENT')")
    @PatchMapping("/patch")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PatchReviewResponseDto> patchReview(@RequestParam Long reviewId, @RequestBody @Valid AddReviewCommand addReviewCommand) {
        return ResponseEntity.ok(reviewService.patchReview(reviewId, addReviewCommand));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRESIDENT', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    void delete(@RequestParam Long reviewId) {
        reviewService.deleteComment(reviewId);
    }


}
