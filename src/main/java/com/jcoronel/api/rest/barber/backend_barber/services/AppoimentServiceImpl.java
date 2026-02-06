package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentUpdateDto;
import com.jcoronel.api.rest.barber.backend_barber.dto.ServiceItemDto;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

            //Listado de servicios del request
            List<ServiceItemDto> servicesRequest = a.getServices();

            Map<Long, ApsEntity> servicesAppoimentBD = appoimentResponse.getAppoimentService()
                    .stream()
                    .collect(Collectors.toMap(
                            aps -> aps.getService().getId(),
                            aps -> aps
                    ));

            servicesRequest.forEach(sr -> {
                System.out.println(servicesAppoimentBD);
                if (servicesAppoimentBD.containsKey(sr.getId())) {
                    System.out.printf("hola entro en la validacion");
                    servicesAppoimentBD.get(sr.getId()).setAmount(sr.getAmount());
                }else{
                    System.out.println("AGREGAR NUEVO SERVICIO");
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

//    @Override
//    public List<Appoiment> findAll() {
//        return List.of();
//    }

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
}
