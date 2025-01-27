package com.example.Reservation_app

import com.example.Reservation_app.Users.User.UserRole
import com.example.Reservation_app.Users.User.UserStatus
import com.example.Reservation_app.Users.UsersService
import spock.lang.Specification
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

import com.example.Reservation_app.Users.User.User
import com.example.Reservation_app.Users.UserRepository
import com.example.Reservation_app.Users.User.dto.GetUserDto
import com.example.Reservation_app.Users.User.dto.PatchUserResponseDto
import com.example.Reservation_app.Users.User.command.PatchUserCommand
import com.example.Reservation_app.Users.User.command.PatchUserRoleCommand
import com.example.Reservation_app.Users.User.command.PatchUserStatusCommand
import com.example.Reservation_app.Users.User.command.SetUserPasswordCommand
import com.example.Reservation_app.Users.User.mapper.UserToGetUserDtoMapper
import com.example.Reservation_app.Users.User.mapper.UserToPatchUserResponseDtoMapper
import com.example.Reservation_app.Appointments.Appointment.Appointment
import com.example.Reservation_app.Appointments.AppointmentRepository
import com.example.Reservation_app.Appointments.AppointmentService

class UsersServiceTest extends Specification {

    def userRepository = Mock(UserRepository)
    def appointmentRepository = Mock(AppointmentRepository)
    def appointmentService = Mock(AppointmentService)
    def userToGetUserDtoMapper = Mock(UserToGetUserDtoMapper)
    def userToPatchUserResponseDtoMapper = Mock(UserToPatchUserResponseDtoMapper)

    def usersService = new UsersService(
            userRepository,
            appointmentRepository,
            appointmentService,
            userToGetUserDtoMapper,
            userToPatchUserResponseDtoMapper
    )

    def "getById should return GetUserDto when user exists"() {
        given:
        Long userId = 1L
        def user = Mock(User)
        def getUserDto = Mock(GetUserDto)

        userRepository.findById(userId) >> Optional.of(user)
        userToGetUserDtoMapper.map(user) >> getUserDto

        when:
        def result = usersService.getById(userId)

        then:
        result == getUserDto
    }

    def "getById should throw ResponseStatusException when user does not exist"() {
        given:
        Long userId = 1L

        userRepository.findById(userId) >> Optional.empty()

        when:
        usersService.getById(userId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "patchUser should update user and return PatchUserResponseDto"() {
        given:
        def command = new PatchUserCommand(userId: 1L, username: 'newUsername', password: 'newPassword', email: 'newEmail', name: 'newName', surname: 'newSurname')
        def user = new User(userId: 1L)
        def responseDto = Mock(PatchUserResponseDto)

        userRepository.findById(command.userId) >> Optional.of(user)
        userRepository.save(user) >> user
        userToPatchUserResponseDtoMapper.map(user) >> responseDto

        when:
        def result = usersService.patchUser(command)

        then:
        result == responseDto
        user.username == 'newUsername'
        user.password == 'newPassword'
        user.email == 'newEmail'
        user.name == 'newName'
        user.surname == 'newSurname'
    }

    def "patchUser should throw ResponseStatusException when user does not exist"() {
        given:
        def command = new PatchUserCommand(userId: 1L)

        userRepository.findById(command.userId) >> Optional.empty()

        when:
        usersService.patchUser(command)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "patchUserRole should update user's role"() {
        given:
        def command = new PatchUserRoleCommand(userId: 1L, userRole: UserRole.PRESIDENT)
        def user = new User(userId: 1L)

        userRepository.findById(command.userId) >> Optional.of(user)

        when:
        usersService.patchUserRole(command)

        then:
        1 * userRepository.save(user)
        user.role == UserRole.PRESIDENT
    }

    def "patchUserRole should throw ResponseStatusException when user does not exist"() {
        given:
        def command = new PatchUserRoleCommand(userId: 1L, userRole: UserRole.PRESIDENT)

        userRepository.findById(command.userId) >> Optional.empty()

        when:
        usersService.patchUserRole(command)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "patchUserStatus should update user's status"() {
        given:
        def command = new PatchUserStatusCommand(userId: 1L, userStatus: UserStatus.ACTIVE)
        def user = new User(userId: 1L)

        userRepository.findById(command.userId) >> Optional.of(user)

        when:
        usersService.patchUserStatus(command)

        then:
        1 * userRepository.save(user)
        user.userStatus == UserStatus.ACTIVE
    }

    def "patchUserStatus should throw ResponseStatusException when user does not exist"() {
        given:
        def command = new PatchUserStatusCommand(userId: 1L, userStatus: 'ACTIVE')

        userRepository.findById(command.userId) >> Optional.empty()

        when:
        usersService.patchUserStatus(command)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "changePassword should throw ResponseStatusException when user does not exist"() {
        given:
        def command = new SetUserPasswordCommand(userId: 1L, newPassword: 'newPassword')

        userRepository.findById(command.userId) >> Optional.empty()

        when:
        usersService.changePassword(command)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "delete should delete user and associated appointments"() {
        given:
        Long userId = 1L
        def user = new User(userId: userId)
        def appointment1 = new Appointment(appointmentId: 1L)
        def appointment2 = new Appointment(appointmentId: 2L)

        userRepository.findById(userId) >> Optional.of(user)
        appointmentRepository.findByClient(user) >> [appointment1, appointment2]

        when:
        usersService.delete(userId)

        then:
        1 * appointmentService.delete(appointment1.appointmentId)
        1 * appointmentService.delete(appointment2.appointmentId)
        1 * userRepository.delete(user)
    }

    def "delete should throw ResponseStatusException when user does not exist"() {
        given:
        Long userId = 1L

        userRepository.findById(userId) >> Optional.empty()

        when:
        usersService.delete(userId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
