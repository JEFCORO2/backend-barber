package com.jcoronel.api.rest.barber.backend_barber.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AppoimentResponseDto {
    private Long id;
    private LocalDateTime date;
    private Integer clientId;
    private List<ServiceItemDto> services;

    public AppoimentResponseDto() {
    }

    public AppoimentResponseDto(Long id, LocalDateTime date, Integer clientId, List<ServiceItemDto> services) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.services = services;
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
}
