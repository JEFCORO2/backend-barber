package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentCreateDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentResponseDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentUpdateDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.ServiceItemDto;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ServiceNotFoundException;
import com.jcoronel.api.rest.barber.backend_barber.repositories.AppoimentServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcoronel.api.rest.barber.backend_barber.entities.Appoiment;
import com.jcoronel.api.rest.barber.backend_barber.entities.ApsEntity;
import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;
import com.jcoronel.api.rest.barber.backend_barber.repositories.AppoimentRepository;
import com.jcoronel.api.rest.barber.backend_barber.repositories.ClientRepository;
import com.jcoronel.api.rest.barber.backend_barber.repositories.ServiceRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AppoimentServiceImpl implements AppoimentService {

    @Autowired
    private AppoimentRepository appoimentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AppoimentServiceRepository apsRepository;

    @Override
    public List<Appoiment> findAll() {
        return (List<Appoiment>) appoimentRepository.findAllAppoimentsTodo();
    }

    @Override
    public Optional<Appoiment> findByID(Long id) {
        return appoimentRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Appoiment> update(Long id, AppoimentUpdateDto a) {

        Optional<Appoiment> citaOptional = appoimentRepository.findById(id);

        if (citaOptional.isPresent()) {
            Appoiment appoimentResponse = citaOptional.get();

            appoimentResponse.setDate(a.getDate());

            if (!appoimentResponse.getAppoimentService().isEmpty()) {
                // Listado de servicios del request
                List<ServiceItemDto> servicesRequest = a.getServices();

                //Set de ids unicos de los serivicios del request
                Set<Long> idsRequest = servicesRequest.stream().map(ids -> ids.getId()).collect(Collectors.toSet());

                // Set de ids unicos con set de la bd por cita
                Set<Long> idsServicesEliminar = appoimentResponse.getAppoimentService()
                        .stream()
                        .filter(ids -> !(idsRequest.contains(ids.getService().getId())))
                        .map(ids -> ids.getId())
                        .collect(Collectors.toSet());

                Map<Long, ApsEntity> servicesAppoimentBD = appoimentResponse.getAppoimentService()
                        .stream()
                        .collect(Collectors.toMap(
                                aps -> aps.getService().getId(),
                                aps -> aps));

                servicesRequest.forEach(sr -> {
                    if (servicesAppoimentBD.containsKey(sr.getId())) {
                        servicesAppoimentBD.get(sr.getId()).setAmount(sr.getAmount());
                    } else {
                        ServiceEntity serviceBD = serviceRepository.findById(sr.getId()).get();
                        ApsEntity newApsEntity = new ApsEntity(appoimentResponse, serviceBD, sr.getAmount());
                        appoimentResponse.getAppoimentService().add(newApsEntity);
                    }
                });

                appoimentResponse.getAppoimentService().removeIf(ids -> idsServicesEliminar.contains(ids.getId()));
            }

            return Optional.of(appoimentResponse);
        }
        return citaOptional;
    }

    @Override
    public Boolean validarMesActual(LocalDateTime mesCita) {
        LocalDateTime hoy = LocalDateTime.now();
        return hoy.getMonth().equals(mesCita.getMonth());
    }

    @Override
    public Appoiment saveAppoiment(Appoiment a) {

        Optional<Client> client = clientRepository.findById(a.getClient().getId());
        client.ifPresent(a::setClient);
        clientRepository.save(a.getClient());

        a.setClient(a.getClient());
        a.setDate(a.getDate());

        List<ApsEntity> listServices = a.getAppoimentService();
        List<ApsEntity> listServicesRest = new ArrayList<>();

        listServices.forEach(aps -> {
            aps.setAppoiment(a);
            listServicesRest.add(aps);

            Long serviceId = aps.getService().getId();
            serviceRepository
                    .findById(serviceId)
                    .orElseThrow(() -> new ServiceNotFoundException("El servicio con el id " + serviceId + " no existe"));

            aps.setAppoiment(a);
            listServicesRest.add(aps);
        });

        a.setAppoimentService(listServicesRest);

        return appoimentRepository.save(a);
    }

    @Override
    public AppoimentResponseDto saveAppoimentDto(AppoimentCreateDto a) {

        Appoiment newAppoiment = new Appoiment();
        List<ApsEntity> newListServices = new ArrayList<>();

        //validar existencia de cliente
        //crear exepcion personalizada si no existe cliente
        Client clientRequest = clientRepository.findById(a.getClientId()).get();

        //te quedaria validar al cliente y los servicios
        newAppoiment.setDate(a.getDate());
        newAppoiment.setClient(clientRequest);

        //crear la cita para generar el id

        //listado servicios
        a.getServices().forEach(aps -> {
            ApsEntity newAps = new ApsEntity();
            ServiceEntity serviceRequest = serviceRepository.findById(aps.getId()).get();

            newAps.setAppoiment(newAppoiment);
            newAps.setService(serviceRequest);
            newAps.setAmount(aps.getAmount());

            newListServices.add(newAps);
        });

        newAppoiment.setAppoimentService(newListServices);
        appoimentRepository.save(newAppoiment);

        AppoimentResponseDto appoimentResponseDto = new AppoimentResponseDto();
        appoimentResponseDto.setId(newAppoiment.getId());
        appoimentResponseDto.setClientId(newAppoiment.getClient().getId());
        appoimentResponseDto.setDate(newAppoiment.getDate());
        appoimentResponseDto.setServices(a.getServices());

        return appoimentResponseDto;
    }
}
