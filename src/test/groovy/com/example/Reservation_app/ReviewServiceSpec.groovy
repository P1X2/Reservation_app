package com.example.Reservation_app

import com.example.Reservation_app.Reviews.ReviewService
import spock.lang.Specification
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

import com.example.Reservation_app.Reviews.Review.Review
import com.example.Reservation_app.Reviews.ReviewRepository
import com.example.Reservation_app.Reviews.Review.command.AddReviewCommand
import com.example.Reservation_app.Reviews.Review.dto.GetReviewDto
import com.example.Reservation_app.Reviews.Review.dto.PatchReviewResponseDto
import com.example.Reservation_app.Reviews.Review.mapper.AddReviewCommandToReviewMapper
import com.example.Reservation_app.Reviews.Review.mapper.ReviewToGetReviewDtoMapper
import com.example.Reservation_app.Reviews.Review.mapper.ReviewToPatchReviewResponseDtoMapper
import com.example.Reservation_app.Appointments.AppointmentService
import com.example.Reservation_app.Appointments.Appointment.Appointment
import com.example.Reservation_app.Utils.ReservationAppUtils

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class ReviewServiceSpec extends Specification {

    def reviewRepository = Mock(ReviewRepository)
    def appointmentService = Mock(AppointmentService)
    def addReviewCommandToReviewMapper = Mock(AddReviewCommandToReviewMapper)
    def reviewToGetReviewDtoMapper = Mock(ReviewToGetReviewDtoMapper)
    def reviewToPatchReviewResponseDtoMapper = Mock(ReviewToPatchReviewResponseDtoMapper)

    def reviewService = new ReviewService(
            reviewRepository,
            appointmentService,
            addReviewCommandToReviewMapper,
            reviewToGetReviewDtoMapper,
            reviewToPatchReviewResponseDtoMapper
    )

    def "getByUserId should return a page of GetReviewDto"() {
        given:
        Long userId = 1L
        Integer page = 0
        Integer pageSize = 10
        String sortBy = "rating"
        String sortDir = "asc"

        def pageable = ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir)
        def reviews = [Mock(Review), Mock(Review)]
        def reviewDtos = [Mock(GetReviewDto), Mock(GetReviewDto)]
        def reviewPage = new PageImpl<>(reviews)

        reviewRepository.findByUsername(userId, _ as Pageable) >> reviewPage
        reviewToGetReviewDtoMapper.map(reviews[0]) >> reviewDtos[0]
        reviewToGetReviewDtoMapper.map(reviews[1]) >> reviewDtos[1]

        when:
        def result = reviewService.getByUserId(userId, page, pageSize, sortBy, sortDir)

        then:
        result.content == reviewDtos
    }

    def "getByServiceId should return a page of GetReviewDto"() {
        given:
        Long serviceId = 1L
        Integer page = 0
        Integer pageSize = 10
        String sortBy = "rating"
        String sortDir = "asc"

        def pageable = ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir)
        def reviews = [Mock(Review), Mock(Review)]
        def reviewDtos = [Mock(GetReviewDto), Mock(GetReviewDto)]
        def reviewPage = new PageImpl<>(reviews)

        reviewRepository.findByService(serviceId, _ as Pageable) >> reviewPage
        reviewToGetReviewDtoMapper.map(reviews[0]) >> reviewDtos[0]
        reviewToGetReviewDtoMapper.map(reviews[1]) >> reviewDtos[1]

        when:
        def result = reviewService.getByServiceId(serviceId, page, pageSize, sortBy, sortDir)

        then:
        result.content == reviewDtos
    }

    def "addReview should save a new review with the appointment"() {
        given:
        Long appointmentId = 1L
        def addReviewCommand = Mock(AddReviewCommand)
        def newReview = Mock(Review)
        def appointment = Mock(Appointment)

        addReviewCommandToReviewMapper.map(addReviewCommand) >> newReview
        appointmentService.getAppointmentById(appointmentId) >> appointment

        when:
        reviewService.addReview(appointmentId, addReviewCommand)

        then:
        1 * newReview.setAppointment(appointment)
        1 * reviewRepository.save(newReview)
    }

    def "addReview should throw ResponseStatusException when appointment does not exist"() {
        given:
        Long appointmentId = 1L
        def addReviewCommand = Mock(AddReviewCommand)
        def newReview = Mock(Review)

        addReviewCommandToReviewMapper.map(addReviewCommand) >> newReview
        appointmentService.getAppointmentById(appointmentId) >> { throw new ResponseStatusException(HttpStatus.NOT_FOUND) }

        when:
        reviewService.addReview(appointmentId, addReviewCommand)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "patchReview should update review and return PatchReviewResponseDto"() {
        given:
        Long reviewId = 1L
        def addReviewCommand = new AddReviewCommand(reviewContent: 'Updated content', rating: 4)
        def reviewRecord = new Review(reviewId: reviewId)
        def responseDto = Mock(PatchReviewResponseDto)

        reviewRepository.findById(reviewId) >> Optional.of(reviewRecord)
        reviewToPatchReviewResponseDtoMapper.map(reviewRecord) >> responseDto

        when:
        def result = reviewService.patchReview(reviewId, addReviewCommand)

        then:
        result == responseDto
        reviewRecord.reviewContent == 'Updated content'
        reviewRecord.rating == 4
        reviewRecord.modifiedOn != null
        1 * reviewRepository.save(reviewRecord)
    }

    def "patchReview should update rating and modifiedOn when reviewContent is null"() {
        given:
        Long reviewId = 1L
        def addReviewCommand = new AddReviewCommand(reviewContent: null, rating: 5)
        def reviewRecord = new Review(reviewId: reviewId, reviewContent: 'Original content')
        def responseDto = Mock(PatchReviewResponseDto)

        reviewRepository.findById(reviewId) >> Optional.of(reviewRecord)
        reviewToPatchReviewResponseDtoMapper.map(reviewRecord) >> responseDto

        when:
        def result = reviewService.patchReview(reviewId, addReviewCommand)

        then:
        result == responseDto
        reviewRecord.reviewContent == 'Original content' // Should remain unchanged
        reviewRecord.rating == 5
        reviewRecord.modifiedOn != null
        1 * reviewRepository.save(reviewRecord)
    }

    def "patchReview should throw ResponseStatusException when review does not exist"() {
        given:
        Long reviewId = 1L
        def addReviewCommand = new AddReviewCommand()

        reviewRepository.findById(reviewId) >> Optional.empty()

        when:
        reviewService.patchReview(reviewId, addReviewCommand)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "deleteComment should delete the review when it exists"() {
        given:
        Long reviewId = 1L
        def review = Mock(Review)

        reviewRepository.findById(reviewId) >> Optional.of(review)

        when:
        reviewService.deleteComment(reviewId)

        then:
        1 * reviewRepository.delete(review)
    }

    def "deleteComment should throw ResponseStatusException when review does not exist"() {
        given:
        Long reviewId = 1L

        reviewRepository.findById(reviewId) >> Optional.empty()

        when:
        reviewService.deleteComment(reviewId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
