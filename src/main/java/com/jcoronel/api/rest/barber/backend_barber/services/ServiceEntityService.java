package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;

public interface ServiceEntityService {
    List<ServiceResponseDto> findAll();
    ServiceResponseDto saveService(ServiceRequestDto c);
    ServiceResponseDto findServiceById(Integer id);
    ServiceResponseDto updateService(Integer id, ServiceRequestDto c);
    ApiResponse<Void> deleteService(Integer id);
}
