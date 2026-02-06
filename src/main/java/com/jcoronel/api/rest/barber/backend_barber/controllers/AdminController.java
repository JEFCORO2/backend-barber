package com.jcoronel.api.rest.barber.backend_barber.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.entities.Location;
import com.jcoronel.api.rest.barber.backend_barber.services.LocationService;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/admin")
public class AdminController {


    @Autowired
    private LocationService locationService;

    //Metodo para cambiar el horario de atencion (UPDATE)
    @PostMapping
    public ResponseEntity<?> cambiarHoraAtencion(Time abierto, Time cerrado) {
        
        //logica para cambiar el horario de atencion
        
        return ResponseEntity.ok().body(abierto);
    }

    @GetMapping("/locals")
    public ResponseEntity<ApiResponse<Location>> findAllLocation() {

        List<Location> locations = locationService.findAll();
        ApiResponse<Location> response = new ApiResponse<>(HttpStatus.ACCEPTED.value(), "Enviado correctamente", locations);
        return ResponseEntity.ok().body(response);
    }
    
}
