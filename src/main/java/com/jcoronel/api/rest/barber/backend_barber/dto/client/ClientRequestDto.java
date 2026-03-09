package com.jcoronel.api.rest.barber.backend_barber.dto.client;

public class ClientRequestDto {
    private String name;
    private String lastname;
    private String tel;
    private String email;
    private String codigo;

    public ClientRequestDto() {
    }

    public ClientRequestDto(String name, String lastname, String tel, String email, String codigo) {
        this.name = name;
        this.lastname = lastname;
        this.tel = tel;
        this.email = email;
        this.codigo = codigo;
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
}
