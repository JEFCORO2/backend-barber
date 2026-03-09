package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.jcoronel.api.rest.barber.backend_barber.dto.*;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ResourceNotFoundException;
import com.jcoronel.api.rest.barber.backend_barber.exceptions.ScheduleAtentionException;
import com.jcoronel.api.rest.barber.backend_barber.repositories.AppoimentServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final AppoimentRepository appoimentRepository;
    private final ServiceRepository serviceRepository;
    private final ClientRepository clientRepository;
    private final AppoimentServiceRepository apsRepository;
    private final LocationService locationService;

    public AppoimentServiceImpl(AppoimentRepository appoimentRepository, ServiceRepository serviceRepository, ClientRepository clientRepository, AppoimentServiceRepository apsRepository, LocationService locationService) {
        this.appoimentRepository = appoimentRepository;
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
        this.apsRepository = apsRepository;
        this.locationService = locationService;
    }

    @Override
    public List<AppoimentListDto> findAll() {
        List<Appoiment> appoimentList  = appoimentRepository.findAllAppoimentsTodo();
        List<AppoimentListDto> appoimentListDto = new ArrayList<AppoimentListDto>();


        appoimentList.forEach(a -> {

            Double totalAppoiment = 0.00;

            AppoimentListDto newAppoimentList = new AppoimentListDto();
            newAppoimentList.setId(a.getId());
            newAppoimentList.setDate(a.getDate());
            newAppoimentList.setClientName(a.getClient().getName() + " " + a.getClient().getLastname());

            //porque for each normal si y no con los functional y que son los lambdas, no eran functional ?
//            for (ApsEntity aps : a.getAppoimentService()){
//                totalAppoiment  += aps.getService().getPrice() * aps.getAmount();
//            }

            totalAppoiment = a.getAppoimentService()
                            .stream()
                            .mapToDouble(aps -> aps.getService().getPrice() * aps.getAmount())
                            .sum();

            newAppoimentList.setTotal(totalAppoiment);
            appoimentListDto.add(newAppoimentList);
        });

        return appoimentListDto;
    }

    @Override
    public AppoimentDetailDto findByID(Long id) {

        Appoiment appoiment = appoimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Appoiment.class.getSimpleName()));

        AppoimentDetailDto appoimentDetail = new AppoimentDetailDto();
        appoimentDetail.setId(appoiment.getId());
        appoimentDetail.setDate(appoiment.getDate());
        appoimentDetail.setClient(appoiment.getClient());
        appoimentDetail.setServices(appoiment.getAppoimentService());

        return appoimentDetail;
    }

    @Override
    public Boolean validarMesActual(LocalDateTime mesCita) {
        LocalDateTime hoy = LocalDateTime.now();
        return hoy.getMonth().equals(mesCita.getMonth());
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteAppoiment(Long id) {
        Appoiment newAppoiment = appoimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Appoiment.class.getSimpleName()));

        appoimentRepository.delete(newAppoiment);

        ApiResponse<Void> responseDelete = new ApiResponse<>();
        responseDelete.setMessage("Appointment successfully deleted");
        responseDelete.setStatus(HttpStatus.OK.value());

        return responseDelete;
    }

    @Override
    @Transactional
    public AppoimentResponseDto saveAppoiment(AppoimentCreateDto a) {

        Appoiment newAppoiment = new Appoiment();
        List<ApsEntity> newListServices = new ArrayList<>();

        Client clientRequest = clientRepository.findById(a.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException(Client.class.getSimpleName()));

        //Podria crear una funcion para no repetir codigo
        if (!locationService.validaHorarioAtencion(a.getLocalId(), a.getDate().toLocalTime())) {
            throw new ScheduleAtentionException("Out of office hours");
        }

        if (!validarMesActual(a.getDate())) {
            throw new ScheduleAtentionException("Appointment outside the current month");
        }

        newAppoiment.setDate(a.getDate());
        newAppoiment.setClient(clientRequest);

        //listado servicios
        a.getServices().forEach(aps -> {
            ApsEntity newAps = new ApsEntity();
            ServiceEntity serviceRequest = serviceRepository.findById(aps.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(ServiceEntity.class.getSimpleName()));

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

    @Override
    @Transactional
    public AppoimentResponseDto updateAppoiment(Long id, AppoimentUpdateDto a) {

        Appoiment appoimentResponse = appoimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Appoiment.class.getSimpleName()));

        AppoimentResponseDto appoimentResponseDto = new AppoimentResponseDto();

        if (!locationService.validaHorarioAtencion(a.getLocalId(), a.getDate().toLocalTime())) {
            throw new ScheduleAtentionException("Out of office hours");
        }

        if (!validarMesActual(a.getDate())) {
            throw new ScheduleAtentionException("Appointment outside the current month");
        }

        appoimentResponse.setDate(a.getDate());

        if (!appoimentResponse.getAppoimentService().isEmpty()) {
            // Listado de servicios del request
            List<ServiceItemDto> servicesRequest = a.getServices();

            //Set de ids unicos de los serivicios del request
            Set<Integer> idsRequest = servicesRequest.stream().map(ids -> ids.getId()).collect(Collectors.toSet());

            // Set de ids unicos con set de la bd por cita
            Set<Integer> idsServicesEliminar = appoimentResponse.getAppoimentService()
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
                    ServiceEntity serviceBD = serviceRepository.findById(sr.getId())
                            .orElseThrow(() -> new ResourceNotFoundException(Client.class.getSimpleName()));

                    ApsEntity newApsEntity = new ApsEntity(appoimentResponse, serviceBD, sr.getAmount());
                    appoimentResponse.getAppoimentService().add(newApsEntity);
                }
            });

            appoimentResponse.getAppoimentService().removeIf(ids -> idsServicesEliminar.contains(ids.getId()));
        }

        appoimentResponseDto.setId(appoimentResponse.getId());
        appoimentResponseDto.setClientId(appoimentResponse.getClient().getId());
        appoimentResponseDto.setDate(appoimentResponse.getDate());
        appoimentResponseDto.setServices(a.getServices());

        return appoimentResponseDto;
    }
}
