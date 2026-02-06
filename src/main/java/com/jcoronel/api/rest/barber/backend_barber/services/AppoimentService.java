package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentUpdateDto;
import com.jcoronel.api.rest.barber.backend_barber.entities.Appoiment;

public interface AppoimentService {
    List<Appoiment> findAll();
    Appoiment saveAppoiment(Appoiment a);
    Optional<Appoiment> findByID(Long id);
    Optional<Appoiment> update(Long id, AppoimentUpdateDto a);
    Boolean validarMesActual(LocalDateTime mesCita);
}
