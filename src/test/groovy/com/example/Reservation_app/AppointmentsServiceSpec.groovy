package com.example.Reservation_app

import com.example.Reservation_app.Appointments.AppointmentRepository
import com.example.Reservation_app.Appointments.AppointmentService
import spock.lang.Specification
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

import com.example.Reservation_app.Appointments.Appointment.Appointment
import com.example.Reservation_app.Appointments.Appointment.command.CreateAppointmentCommand
import com.example.Reservation_app.Appointments.Appointment.dto.CreateAppointmentResponseDto
import com.example.Reservation_app.Appointments.Appointment.dto.GetAppointmentDto
import com.example.Reservation_app.Appointments.Appointment.AppointmentStatus
import com.example.Reservation_app.Appointments.Appointment.mapper.AppointmentToCreateAppointmentResponseDtoMapper
import com.example.Reservation_app.Appointments.Appointment.mapper.AppointmentToGetAppoinmentDtoMapper
import com.example.Reservation_app.Appointments.Appointment.mapper.CreateAppointmentCommandToAppointmentDtoMapper
import com.example.Reservation_app.Reviews.ReviewRepository
import com.example.Reservation_app.Users.User.User
import com.example.Reservation_app.Users.UserRepository
import com.example.Reservation_app.Utils.ReservationAppUtils

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class AppointmentServiceSpec extends Specification {

    def appointmentRepository = Mock(AppointmentRepository)
    def userRepository = Mock(UserRepository)
    def reviewRepository = Mock(ReviewRepository)
    def appointmentToGetAppoinmentDtoMapper = Mock(AppointmentToGetAppoinmentDtoMapper)
    def createAppointmentCommandToAppointmentDtoMapper = Mock(CreateAppointmentCommandToAppointmentDtoMapper)
    def createAppointmentResponseDtoMapper = Mock(AppointmentToCreateAppointmentResponseDtoMapper)

    def appointmentService = new AppointmentService(
            appointmentRepository,
            userRepository,
            reviewRepository,
            appointmentToGetAppoinmentDtoMapper,
            createAppointmentCommandToAppointmentDtoMapper,
            createAppointmentResponseDtoMapper
    )

    def "getByUserId should return a page of GetAppointmentDto when user exists"() {
        given:
        Long userId = 1L
        Integer page = 0
        Integer pageSize = 10
        String sortBy = "date"
        String sortDir = "asc"

        def user = Mock(User)
        def pageable = ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir)
        def appointments = [Mock(Appointment), Mock(Appointment)]
        def appointmentDtos = [Mock(GetAppointmentDto), Mock(GetAppointmentDto)]
        def appointmentPage = new PageImpl<>(appointments)

        userRepository.findById(userId) >> Optional.of(user)
        appointmentRepository.findByClient(user, _ as Pageable) >> appointmentPage
        appointmentToGetAppoinmentDtoMapper.map(appointments[0]) >> appointmentDtos[0]
        appointmentToGetAppoinmentDtoMapper.map(appointments[1]) >> appointmentDtos[1]

        when:
        def result = appointmentService.getByUserId(userId, page, pageSize, sortBy, sortDir)

        then:
        result.content == appointmentDtos
    }

    def "getByUserId should throw ResponseStatusException when user does not exist"() {
        given:
        Long userId = 1L

        userRepository.findById(userId) >> Optional.empty()

        when:
        appointmentService.getByUserId(userId, 0, 10, 'date', 'asc')

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "create should save new appointment and return CreateAppointmentResponseDto"() {
        given:
        def createAppointmentCommand = Mock(CreateAppointmentCommand)
        def appointment = Mock(Appointment)
        def responseDto = Mock(CreateAppointmentResponseDto)

        createAppointmentCommandToAppointmentDtoMapper.map(createAppointmentCommand) >> appointment
        createAppointmentResponseDtoMapper.map(appointment) >> responseDto

        when:
        def result = appointmentService.create(createAppointmentCommand)

        then:
        1 * appointmentRepository.save(appointment)
        result == responseDto
    }

    def "updateStatus should throw ResponseStatusException when appointment does not exist"() {
        given:
        Long appointmentId = 1L
        def newStatus = AppointmentStatus.COMPLETED

        appointmentRepository.findById(appointmentId) >> Optional.empty()

        when:
        appointmentService.updateStatus(appointmentId, newStatus)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "delete should delete appointment and associated review when both exist"() {
        given:
        Long appointmentId = 1L
        def appointment = new Appointment(appointmentId: appointmentId)
        Long reviewId = 2L

        appointmentRepository.findById(appointmentId) >> Optional.of(appointment)
        appointmentRepository.findReviewIdToDelete(appointmentId) >> Optional.of(reviewId)

        when:
        appointmentService.delete(appointmentId)

        then:
        1 * reviewRepository.deleteById(reviewId)
        1 * appointmentRepository.delete(appointment)
    }

    def "delete should throw ResponseStatusException when appointment does not exist"() {
        given:
        Long appointmentId = 1L

        appointmentRepository.findById(appointmentId) >> Optional.empty()

        when:
        appointmentService.delete(appointmentId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "delete should throw ResponseStatusException when review ID not found"() {
        given:
        Long appointmentId = 1L
        def appointment = new Appointment(appointmentId: appointmentId)

        appointmentRepository.findById(appointmentId) >> Optional.of(appointment)
        appointmentRepository.findReviewIdToDelete(appointmentId) >> Optional.empty()

        when:
        appointmentService.delete(appointmentId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "getAppointmentById should return appointment when it exists"() {
        given:
        Long appointmentId = 1L
        def appointment = Mock(Appointment)

        appointmentRepository.findById(appointmentId) >> Optional.of(appointment)

        when:
        def result = appointmentService.getAppointmentById(appointmentId)

        then:
        result == appointment
    }

    def "getAppointmentById should throw ResponseStatusException when appointment does not exist"() {
        given:
        Long appointmentId = 1L

        appointmentRepository.findById(appointmentId) >> Optional.empty()

        when:
        appointmentService.getAppointmentById(appointmentId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
