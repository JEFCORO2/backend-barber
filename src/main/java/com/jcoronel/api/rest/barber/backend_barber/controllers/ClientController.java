package com.jcoronel.api.rest.barber.backend_barber.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import com.jcoronel.api.rest.barber.backend_barber.services.ClientService;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public List<Client> findAll(){
        return service.findAll();
    }
}
