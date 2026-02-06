package com.jcoronel.api.rest.barber.backend_barber.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Integer>{

}
