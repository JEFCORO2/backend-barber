package com.jcoronel.api.rest.barber.backend_barber.services;

import java.util.ArrayList;
import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientRequestDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.client.ClientResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ResourceNotFoundException;
import com.jcoronel.api.rest.barber.backend_barber.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import com.jcoronel.api.rest.barber.backend_barber.repositories.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientResponseDto> findAll() {

        Iterable<Client> clientList = clientRepository.findAll();
        List<ClientResponseDto> clientResponseList = new ArrayList<ClientResponseDto>();

        clientList.forEach(c -> {
            ClientResponseDto clientResponseDto = clientMapper.clientToDto(c);
            clientResponseList.add(clientResponseDto);
        });

        return clientResponseList;
    }

    @Override
    @Transactional
    public ClientResponseDto saveClient(ClientRequestDto c) {
        Client newClient = clientMapper.dtoToClient(c);
        clientRepository.save(newClient);
        return clientMapper.clientToDto(newClient);
    }

    @Override
    public ClientResponseDto findClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Client.class.getSimpleName()));

        return clientMapper.clientToDto(client);
    }

    @Override
    @Transactional
    public ClientResponseDto updateClient(Integer id, ClientRequestDto c) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Client.class.getSimpleName()));

        client.setCodigo(c.getCodigo());
        client.setEmail(c.getEmail());
        client.setLastname(c.getLastname());
        client.setName(c.getName());
        client.setTel(c.getTel());

        return clientMapper.clientToDto(client);
    }

    @Override
    public ApiResponse<Void> deleteClient(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Client.class.getSimpleName()));

        clientRepository.delete(client);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Client successfully deleted");
        apiResponse.setStatus(HttpStatus.OK.value());

        return apiResponse;
    }
}
