package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentUpdateDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.ServiceItemDto;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.jcoronel.api.rest.barber.backend_barber.entities.Appoiment;
import com.jcoronel.api.rest.barber.backend_barber.entities.ApsEntity;
import com.jcoronel.api.rest.barber.backend_barber.entities.Client;
import com.jcoronel.api.rest.barber.backend_barber.entities.ServiceEntity;
import com.jcoronel.api.rest.barber.backend_barber.repositories.AppoimentRepository;
import com.jcoronel.api.rest.barber.backend_barber.repositories.AppoimentServiceRepository;
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

            //si el request viene vacio, necesito servicios para hacer esa comparacion

            // Listado de servicios del request
            List<ServiceItemDto> servicesRequest = a.getServices();

            //Set de ids unicos de los serivicios del request
            Set<Long> idsRequest = servicesRequest.stream().map(ids -> ids.getId()).collect(Collectors.toSet());

            // Set de ids unicos con set de la bd por cita
            // Es normal el orden? , o se podria cambiar o hay algun orden en especifico
            Set<Long> idsServicesEliminar = appoimentResponse.getAppoimentService()
                    .stream()
                    .filter(ids -> !(idsRequest.contains(ids.getService().getId())))
                    .map(ids -> ids.getId())
                    .collect(Collectors.toSet());

            ///System.out.println(idsServicesEliminar);
            //se deberia eliminar por ids de los servicios mas no por el id 
            //idsServicesEliminar.forEach(ids -> apsRepository.deleteByIdServices(id, ids));
            idsServicesEliminar.forEach(ids -> System.out.println(ids));

            Map<Long, ApsEntity> servicesAppoimentBD = appoimentResponse.getAppoimentService()
                    .stream()
                    .collect(Collectors.toMap(
                            aps -> aps.getService().getId(),
                            aps -> aps));

            servicesRequest.forEach(sr -> {
                if (servicesAppoimentBD.containsKey(sr.getId())) {
                    servicesAppoimentBD.get(sr.getId()).setAmount(sr.getAmount());
                } else {
                    // validar si existe el servicio
                    // que pasaria con los servicios que ya no estan en el json
                    ServiceEntity serviceBD = serviceRepository.findById(sr.getId()).get();
                    ApsEntity newApsEntity = new ApsEntity(appoimentResponse, serviceBD, sr.getAmount());
                    apsRepository.save(newApsEntity);
                    //appoimentResponse.getAppoimentService().add(newApsEntity);
                }
            });

            return Optional.of(appoimentResponse);
        }
        return null;
    }

    @Override
    public Boolean validarMesActual(LocalDateTime mesCita) {
        LocalDateTime hoy = LocalDateTime.now();
        return hoy.getMonth().equals(mesCita.getMonth());
    }

    // @Override
    // public List<Appoiment> findAll() {
    // return List.of();
    // }

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
                    .orElseThrow(
                            () -> new ServiceNotFoundException("El servicio con el id " + serviceId + " no existe"));

            aps.setAppoiment(a);
            listServicesRest.add(aps);
        });

        a.setAppoimentService(listServicesRest);

        return appoimentRepository.save(a);
    }
}
