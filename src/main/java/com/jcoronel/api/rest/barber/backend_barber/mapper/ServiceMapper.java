package com.jcoronel.api.rest.barber.backend_barber.mapper;

import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceResponseDto serviceToDto(ServiceEntity s);
    ServiceEntity dtoToService(ServiceRequestDto serviceRequestDto);
}
