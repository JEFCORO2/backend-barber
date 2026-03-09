package com.jcoronel.api.rest.barber.backend_barber.dto;

import com.jcoronel.api.rest.barber.backend_barber.entities.ApsEntity;
import com.jcoronel.api.rest.barber.backend_barber.entities.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppoimentDetailDto {
    private Long id;
    private LocalDateTime date;
    private Client client;
    private List<ApsEntity> services = new ArrayList<>();

    public AppoimentDetailDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ApsEntity> getServices() {
        return services;
    }

    public void setServices(List<ApsEntity> services) {
        this.services = services;
    }
}
