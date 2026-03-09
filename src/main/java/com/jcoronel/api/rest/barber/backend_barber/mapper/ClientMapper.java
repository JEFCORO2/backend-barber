package com.jcoronel.api.rest.barber.backend_barber.mapper;

import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDto clientToDto(Client client);
    Client dtoToClient(ClientRequestDto clientRequestDto);
}
