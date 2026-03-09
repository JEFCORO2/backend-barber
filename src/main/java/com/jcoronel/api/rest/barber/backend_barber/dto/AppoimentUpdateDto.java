package com.jcoronel.api.rest.barber.backend_barber.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppoimentUpdateDto {
    @NotNull
    @FutureOrPresent
    private LocalDateTime date;

    @NotNull
    private Integer client;

    @NotNull
    private Integer localId;

    private List<ServiceItemDto> services;

    public AppoimentUpdateDto() {
    }

    public AppoimentUpdateDto(LocalDateTime date, Integer client, Integer localId, List<ServiceItemDto> services) {
        this.date = date;
        this.client = client;
        this.localId = localId;
        this.services = services;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<ServiceItemDto> getServices() {
        return services;
    }

    public Integer getclient() {
        return client;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public void setServices(List<ServiceItemDto> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "AppoimentUpdateDto{" +
                "date=" + date +
                ", client=" + client +
                ", services=" + services +
                '}';
    }
}
