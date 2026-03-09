package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientResponseDto;

public interface ClientService {
    List<ClientResponseDto> findAll();
    ClientResponseDto saveClient(ClientRequestDto c);
    ClientResponseDto findClientById(Integer id);
    ClientResponseDto updateClient(Integer id, ClientRequestDto c);
    ApiResponse<Void> deleteClient(Integer id);
}
