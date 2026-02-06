package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;
import com.jcoronel.api.rest.barber.backend_barber.repositories.ServiceRepository;

@Service
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<ServiceEntity> findAll() {
        return (List<ServiceEntity>) serviceRepository.findAll();
    }
}
