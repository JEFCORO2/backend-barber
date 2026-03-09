package com.jcoronel.api.rest.barber.backend_barber.controllers;

import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.services.ServiceResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;
import com.jcoronel.api.rest.barber.backend_barber.services.ServiceEntityService;

@CrossOrigin(origins = {
    "http://localhost:5500",
    "http://127.0.0.1:5500"
})
@RestController
@RequestMapping("api/services")
public class ServiceController {

    private final ServiceEntityService serviceEntityService;

    public ServiceController(ServiceEntityService service) {
        this.serviceEntityService = service;
    }

    @GetMapping
    public List<ServiceResponseDto> findAll(){
        return serviceEntityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> show(@PathVariable Integer id) {
        return ResponseEntity.ok().body(serviceEntityService.findServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> createClient(@Valid @RequestBody ServiceRequestDto serviceRequestDto){
        return ResponseEntity.ok().body(serviceEntityService.saveService(serviceRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateClient(@Valid @RequestBody ServiceRequestDto serviceRequestDto,
                                                          @PathVariable Integer id){
        return ResponseEntity.ok().body(serviceEntityService.updateService(id,serviceRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Integer id){
        return ResponseEntity.ok().body(serviceEntityService.deleteService(id));
    }
}
