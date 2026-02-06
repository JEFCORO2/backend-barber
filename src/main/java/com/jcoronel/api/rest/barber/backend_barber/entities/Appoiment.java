package com.jcoronel.api.rest.barber.backend_barber.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "citas")
public class Appoiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha")
    @FutureOrPresent
    private LocalDateTime date;

    @Valid
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "appoiment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApsEntity> appoimentService = new ArrayList<>();

    public Appoiment() {
    }

    public Appoiment(LocalDateTime date, Client client, List<ApsEntity> appoimentService) {
        this.date = date;
        this.client = client;
        this.appoimentService = appoimentService;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ApsEntity> getAppoimentService() {
        return appoimentService;
    }

    public void setAppoimentService(List<ApsEntity> appoimentService) {
        this.appoimentService = appoimentService;
    }

    @Override
    public String toString() {
        return "Appoiment [id=" + id + ", date=" + date + ", client=" + client;
    }
}
