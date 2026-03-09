package com.jcoronel.api.rest.barber.backend_barber.controllers;

import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentDetailDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jcoronel.api.rest.barber.backend_barber.services.ClientService;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponseDto> findAll(){
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> show(@PathVariable Integer id) {
        return ResponseEntity.ok().body(clientService.findClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto){
        return ResponseEntity.ok().body(clientService.saveClient(clientRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@Valid @RequestBody ClientRequestDto clientRequestDto,
                                                          @PathVariable Integer id){
        return ResponseEntity.ok().body(clientService.updateClient(id,clientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Integer id){
        return ResponseEntity.ok().body(clientService.deleteClient(id));
    }
}
