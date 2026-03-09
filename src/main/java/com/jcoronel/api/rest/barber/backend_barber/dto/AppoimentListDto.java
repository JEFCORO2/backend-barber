package com.jcoronel.api.rest.barber.backend_barber.dto;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;

import java.time.LocalDateTime;

public class AppoimentListDto {
    private Long id;
    private LocalDateTime date;
    private String clientName;
    private Double total;

    public AppoimentListDto() {
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
