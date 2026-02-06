package com.jcoronel.api.rest.barber.backend_barber.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cargos")
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "enable")
    private Boolean enable = true;

    public CargoEntity() {
    }

    public CargoEntity(String nombre, Boolean enable) {
        this.nombre = nombre;
        this.enable = enable;
    }

    // --- Getters y Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "CargoEntity [id=" + id + ", nombre=" + nombre + ", enable=" + enable + "]";
    }
}