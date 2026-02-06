package com.jcoronel.api.rest.barber.backend_barber.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppoimentUpdateDto {
    private LocalDateTime date;
    private Integer client;
    private List<ServiceItemDto> services;

    public AppoimentUpdateDto(LocalDateTime date, Integer client, List<ServiceItemDto> services) {
        this.date = date;
        this.client = client;
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

    @Override
    public String toString() {
        return "AppoimentUpdateDto{" +
                "date=" + date +
                ", client=" + client +
                ", services=" + services +
                '}';
    }
}
