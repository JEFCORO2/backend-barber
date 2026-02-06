package com.jcoronel.api.rest.barber.backend_barber.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastname;
    private String tel;
    private String address;
    private String dni;
    private boolean enable;

    @OneToMany(mappedBy = "id")
    private List<CargoEntity> cargo;

    public EmpleadoEntity(){
        cargo = new ArrayList<>();
    }

    public EmpleadoEntity(String name, String lastname, String tel, String address, String dni,
            boolean enable, List<CargoEntity> cargo) {
        this.name = name;
        this.lastname = lastname;
        this.tel = tel;
        this.address = address;
        this.dni = dni;
        this.enable = enable;
        this.cargo = cargo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "EmpleadoEntity [id=" + id + ", name=" + name + ", lastname=" + lastname + ", tel=" + tel + ", address="
                + address + ", dni=" + dni + ", enable=" + enable + ", cargo=" + cargo + "]";
    }

    public List<CargoEntity> getCargo() {
        return cargo;
    }

    public void setCargo(List<CargoEntity> cargo) {
        this.cargo = cargo;
    }
}
