package com.jcoronel.api.rest.barber.backend_barber.entities;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locales")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private LocalTime hora_apertura;
    private LocalTime hora_cierre;
    private Boolean enabled;

    public Location(){}

    public Location(Integer id, String address, LocalTime hora_apertura, LocalTime hora_cierre, Boolean enabled) {
        this.id = id;
        this.address = address;
        this.hora_apertura = hora_apertura;
        this.hora_cierre = hora_cierre;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalTime getHora_apertura() {
        return hora_apertura;
    }
    public void setHora_apertura(LocalTime hora_apertura) {
        this.hora_apertura = hora_apertura;
    }
    public LocalTime getHora_cierre() {
        return hora_cierre;
    }
    public void setHora_cierre(LocalTime hora_cierre) {
        this.hora_cierre = hora_cierre;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
