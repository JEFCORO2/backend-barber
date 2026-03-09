package com.jcoronel.api.rest.barber.backend_barber.dto.services;

public class ServiceRequestDto {
    private String name;
    private Double price;

    public ServiceRequestDto() {
    }

    public ServiceRequestDto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
