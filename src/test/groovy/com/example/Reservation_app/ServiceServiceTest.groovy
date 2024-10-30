package com.example.Reservation_app

import com.example.Reservation_app.Services.ServiceService
import spock.lang.Specification
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

import com.example.Reservation_app.Services.Service.Service
import com.example.Reservation_app.Services.ServiceRepository
import com.example.Reservation_app.Services.Service.command.AddServiceCommand
import com.example.Reservation_app.Services.Service.command.PatchServiceCommand
import com.example.Reservation_app.Services.Service.dto.GetServiceDto
import com.example.Reservation_app.Services.Service.dto.PatchServiceResponseDto
import com.example.Reservation_app.Services.Service.mapper.AddServiceCommandToServiceMapper
import com.example.Reservation_app.Services.Service.mapper.ServiceToGetServiceDtoMapper
import com.example.Reservation_app.Services.Service.mapper.ServiceToPatchServiceResponseDto
import com.example.Reservation_app.Utils.ReservationAppUtils

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class ServiceServiceTest extends Specification {

    def serviceRepository = Mock(ServiceRepository)
    def addServiceCommandToServiceMapper = Mock(AddServiceCommandToServiceMapper)
    def serviceToGetServiceDtoMapper = Mock(ServiceToGetServiceDtoMapper)
    def serviceToPatchServiceResponseDto = Mock(ServiceToPatchServiceResponseDto)

    def serviceService = new ServiceService(
            serviceRepository,
            addServiceCommandToServiceMapper,
            serviceToGetServiceDtoMapper,
            serviceToPatchServiceResponseDto
    )

    def "findById should return GetServiceDto when service exists"() {
        given:
        Long serviceId = 1L
        def service = Mock(Service)
        def getServiceDto = Mock(GetServiceDto)

        serviceRepository.findById(serviceId) >> Optional.of(service)
        serviceToGetServiceDtoMapper.map(service) >> getServiceDto

        when:
        def result = serviceService.findById(serviceId)

        then:
        result == getServiceDto
    }

    def "findById should throw ResponseStatusException when service does not exist"() {
        given:
        Long serviceId = 1L

        serviceRepository.findById(serviceId) >> Optional.empty()

        when:
        serviceService.findById(serviceId)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "findByName should return GetServiceDto when service exists"() {
        given:
        String name = "testService"
        String capitalized = "TestService" // Simulating StringUtils.capitalize(name)
        def service = Mock(Service)
        def getServiceDto = Mock(GetServiceDto)

        serviceRepository.findByName(capitalized) >> Optional.of(service)
        serviceToGetServiceDtoMapper.map(service) >> getServiceDto

        when:
        def result = serviceService.findByName(name)

        then:
        result == getServiceDto
    }

    def "findByName should throw ResponseStatusException when service does not exist"() {
        given:
        String name = "testService"
        String capitalized = "TestService" // Simulating StringUtils.capitalize(name)

        serviceRepository.findByName(capitalized) >> Optional.empty()

        when:
        serviceService.findByName(name)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "findAll should return a page of GetServiceDto"() {
        given:
        Integer page = 0
        Integer pageSize = 10
        String sortBy = "name"
        String sortDir = "asc"

        def pageable = ReservationAppUtils.getPageMetadata(page, pageSize, sortBy, sortDir)
        def services = [Mock(Service), Mock(Service)]
        def serviceDtos = [Mock(GetServiceDto), Mock(GetServiceDto)]
        def servicePage = new PageImpl<>(services)

        serviceRepository.findAll(_ as Pageable) >> servicePage
        serviceToGetServiceDtoMapper.map(services[0]) >> serviceDtos[0]
        serviceToGetServiceDtoMapper.map(services[1]) >> serviceDtos[1]

        when:
        def result = serviceService.findAll(page, pageSize, sortBy, sortDir)

        then:
        result.content == serviceDtos
    }

    def "addNewService should save a new service"() {
        given:
        def command = Mock(AddServiceCommand)
        def service = Mock(Service)

        addServiceCommandToServiceMapper.map(command) >> service

        when:
        serviceService.addNewService(command)

        then:
        1 * serviceRepository.save(service)
    }

    def "patchService should update service and return PatchServiceResponseDto"() {
        given:
        def command = new PatchServiceCommand(
                serviceId: 1L,
                name: 'UpdatedName',
                description: 'UpdatedDescription',
                durationMinutes: 60,
                price: new BigDecimal("99.99")
        )
        def service = new Service(serviceId: 1L)
        def responseDto = Mock(PatchServiceResponseDto)

        serviceRepository.findById(command.serviceId) >> Optional.of(service)
        serviceRepository.save(service) >> service
        serviceToPatchServiceResponseDto.map(service) >> responseDto

        when:
        def result = serviceService.patchService(command)

        then:
        result == responseDto
        service.name == 'UpdatedName'
        service.description == 'UpdatedDescription'
        service.durationMinutes == 60
        service.price == 99
        service.modifiedOn != null
    }

    def "patchService should throw ResponseStatusException when service does not exist"() {
        given:
        def command = new PatchServiceCommand(serviceId: 1L)

        serviceRepository.findById(command.serviceId) >> Optional.empty()

        when:
        serviceService.patchService(command)

        then:
        def e = thrown(ResponseStatusException)
        e.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
