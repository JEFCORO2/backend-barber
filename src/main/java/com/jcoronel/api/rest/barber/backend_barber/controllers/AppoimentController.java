package com.jcoronel.api.rest.barber.backend_barber.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jcoronel.api.rest.barber.backend_barber.dto.*;
import com.jcoronel.api.rest.barber.backend_barber.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jcoronel.api.rest.barber.backend_barber.entities.Appoiment;
import com.jcoronel.api.rest.barber.backend_barber.services.AppoimentService;
import com.jcoronel.api.rest.barber.backend_barber.services.LocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/appoiments")
public class AppoimentController {

    @Autowired
    private AppoimentService service;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<AppoimentDetailDto> show(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findByID(id));
    }

    @GetMapping
    public List<AppoimentListDto> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<AppoimentResponseDto> createAppoiment(@Valid @RequestBody AppoimentCreateDto appoiment) {
        return ResponseEntity.ok().body(service.saveAppoiment(appoiment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppoimentResponseDto> updateAppoiment(@Valid @RequestBody AppoimentUpdateDto appoiment,
                                             @PathVariable Long id) {
        return ResponseEntity.ok().body(service.updateAppoiment(id,appoiment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppoiment(@PathVariable Long id){
        return ResponseEntity.ok().body(service.deleteAppoiment(id));
    }
}
