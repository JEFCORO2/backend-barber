package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.ArrayList;
import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ResourceNotFoundException;
import com.jcoronel.api.rest.barber.backend_barber.mapper.ServiceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jcoronel.api.rest.barber.backend_barber.repositories.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceEntityServiceImpl implements ServiceEntityService {
    
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceEntityServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }
    
    @Override
    public List<ServiceResponseDto> findAll() {

        Iterable<ServiceEntity> serviceList = serviceRepository.findAll();
        List<ServiceResponseDto> serviceResponseList = new ArrayList<ServiceResponseDto>();

        serviceList.forEach(c -> {
            ServiceResponseDto serviceResponseDto = serviceMapper.serviceToDto(c);
            serviceResponseList.add(serviceResponseDto);
        });

        return serviceResponseList;
    }

    @Override
    @Transactional
    public ServiceResponseDto saveService(ServiceRequestDto c) {
        ServiceEntity newServiceEntity = serviceMapper.dtoToService(c);
        serviceRepository.save(newServiceEntity);
        return serviceMapper.serviceToDto(newServiceEntity);
    }

    @Override
    public ServiceResponseDto findServiceById(Integer id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ServiceEntity.class.getSimpleName()));

        return serviceMapper.serviceToDto(service);
    }

    @Override
    @Transactional
    public ServiceResponseDto updateService(Integer id, ServiceRequestDto c) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ServiceEntity.class.getSimpleName()));

        service.setPrice(c.getPrice());
        service.setName(c.getName());

        return serviceMapper.serviceToDto(service);
    }

    @Override
    public ApiResponse<Void> deleteService(Integer id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ServiceEntity.class.getSimpleName()));

        serviceRepository.delete(service);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Service successfully deleted");
        apiResponse.setStatus(HttpStatus.OK.value());

        return apiResponse;
    }
}
