package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;

public interface ClientService {
    List<Client> findAll();
    boolean isFoundClient(Integer id);
}
