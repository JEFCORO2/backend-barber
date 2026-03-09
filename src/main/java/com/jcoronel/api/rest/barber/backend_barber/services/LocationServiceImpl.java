package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcoronel.api.rest.barber.backend_barber.entities.Location;
import com.jcoronel.api.rest.barber.backend_barber.repositories.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public Boolean validaHorarioAtencion(Integer id, LocalTime time) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Location.class.getSimpleName()));

        LocalTime horaApertura = location.getHora_apertura();
        LocalTime horaCierre = location.getHora_cierre();

        //si es sin negacion seria true en horas incorrectas , y el and es para validar que este en el rango de las dos horas
        return !time.isBefore(horaApertura) && !time.isAfter(horaCierre);
    }
}
