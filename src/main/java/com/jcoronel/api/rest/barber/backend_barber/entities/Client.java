package com.jcoronel.api.rest.barber.backend_barber.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "El id debe ser un numero, ademas de mayor a 0")
    private Integer id;

    //@NotNull
    @Column(name = "nombre")
    @Size(min = 3 , max = 40)
    private String name;

    //@NotNull
    @Column(name = "apellido")
    @Size(min = 3 , max = 40)
    private String lastname;
    
    @Column(name = "telefono")
    @Size(min = 9)
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "codigo")
    private String codigo;

    public Client() {
    }

    public Client(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Client(Integer id, String name, String lastname, String tel, String codigo, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.tel = tel;
        this.codigo = codigo;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", lastname=" + lastname + ", tel=" + tel + ", email=" + email
                + "]";
    }
}
