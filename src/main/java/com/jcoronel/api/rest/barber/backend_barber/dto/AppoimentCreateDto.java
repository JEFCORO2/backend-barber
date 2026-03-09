package com.jcoronel.api.rest.barber.backend_barber.dto;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class AppoimentCreateDto {
    @NotNull
    @FutureOrPresent
    private LocalDateTime date;

    @NotNull
    private Integer clientId;

    @NotNull
    private List<ServiceItemDto> services;

    private Integer localId;

    public AppoimentCreateDto() {
    }

    public AppoimentCreateDto(LocalDateTime date, Integer clientId, Integer localId, List<ServiceItemDto> services) {
        this.date = date;
        this.clientId = clientId;
        this.localId = localId;
        this.services = services;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<ServiceItemDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceItemDto> services) {
        this.services = services;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }
}
