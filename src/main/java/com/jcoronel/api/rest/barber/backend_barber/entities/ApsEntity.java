package com.jcoronel.api.rest.barber.backend_barber.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas_servicios")
public class ApsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Appoiment appoiment;

    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id", nullable = false)
    private ServiceEntity service;

    @Column(name = "cantidad")
    private Integer amount;

    public ApsEntity(){};

    public ApsEntity(ServiceEntity service, Integer amount) {
        this.service = service;
        this.amount = amount;
    }

    public ApsEntity(Appoiment appoiment, ServiceEntity service, Integer amount) {
        this.appoiment = appoiment;
        this.service = service;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appoiment getAppoiment() {
        return appoiment;
    }

    public void setAppoiment(Appoiment appoiment) {
        this.appoiment = appoiment;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ApsEntity [service=" + service + ", amount=" + amount + "]";
    }
}
