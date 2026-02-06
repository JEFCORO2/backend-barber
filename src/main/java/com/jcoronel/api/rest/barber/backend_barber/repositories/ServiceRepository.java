package com.jcoronel.api.rest.barber.backend_barber.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Long>{
    
}
