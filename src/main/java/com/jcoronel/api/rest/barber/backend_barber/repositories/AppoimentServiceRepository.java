package com.jcoronel.api.rest.barber.backend_barber.repositories;

import com.jcoronel.api.rest.barber.backend_barber.entities.ApsEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppoimentServiceRepository extends CrudRepository<ApsEntity, Long>{
}
