package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalTime;
import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.entities.Location;

public interface LocationService {
    List<Location> findAll();
    Boolean validaHorarioAtencion(Integer id, LocalTime date);
}
