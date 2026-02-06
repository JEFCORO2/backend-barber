package com.jcoronel.api.rest.barber.backend_barber.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jcoronel.api.rest.barber.backend_barber.dto.AppoimentUpdateDto;
import com.jcoronel.api.rest.barber.backend_barber.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcoronel.api.rest.barber.backend_barber.dto.ApiResponse;
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
    public ResponseEntity<?> show(@PathVariable Long id) {

        Optional<Appoiment> appoimentOptional = service.findByID(id);

        if (appoimentOptional.isPresent()) {
            return ResponseEntity.ok(appoimentOptional.orElseThrow());
        }

        return ResponseEntity.badRequest().body(Map.of("error", "La cita con id, " + id + " no existe"));
    }

    @GetMapping
    public List<Appoiment> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Appoiment>> createAppoiment(@Valid @RequestBody Appoiment appoiment,
            BindingResult result) {

        Map<String, String> errors = validation(result, appoiment);

        if (!errors.isEmpty() || result.hasFieldErrors() || appoiment.getClient().getId() == null) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    "errores en la peticion", null, errors));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.ACCEPTED.value(),
                "enviado Correctamente", service.saveAppoiment(appoiment), validation(result, appoiment)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppoiment(@Valid @RequestBody AppoimentUpdateDto appoiment, BindingResult result,
                                             @PathVariable Long id) {

        Optional<Appoiment> appoimentOptional = service.update(id, appoiment);

        if (appoimentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(appoimentOptional.orElseThrow());
        }

        return ResponseEntity.badRequest().body(Map.of("error", "La cita con id, " + id + " no existe"));
    }

    private Map<String, String> validation(BindingResult result, Appoiment appoiment) {
        Map<String, String> errors = new HashMap<>();

        if (!locationService.validaHorarioAtencion(1,appoiment.getDate().toLocalTime())) {
            errors.put("location.hour", "la hora no es valida, escoje otra dentro del rango");
        }

        if (!service.validarMesActual(appoiment.getDate())) {
            errors.put("appoiment.month", "solo puedes sacar cita en el mes actual");
        }

        if (appoiment.getClient() == null || appoiment.getClient().getId() == null) {
            result.rejectValue("client.id", "client.id.null", "no puede ser nulo o debe tener un id");
        }

/*        if (appoiment.getAppoimentService() == null) {
            result.rejectValue("appoiment.service", "services.null", "deberia tener servicios asociados");
        }*/

        if (!clientService.isFoundClient(appoiment.getClient().getId())) {
            errors.put("client.id", "no existe cliente con el id : " + appoiment.getClient().getId());
        }

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return errors;
    }
}
