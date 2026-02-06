package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;

public interface ServiceService {
    List<ServiceEntity> findAll(); 
}
