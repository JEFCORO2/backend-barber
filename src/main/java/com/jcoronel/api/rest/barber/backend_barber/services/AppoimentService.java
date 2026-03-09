package com.jcoronel.api.rest.barber.backend_barber.services;

import java.time.LocalDateTime;
import java.util.List;

import com.jcoronel.api.rest.barber.backend_barber.dto.*;

public interface AppoimentService {
    List<AppoimentListDto> findAll();
    AppoimentResponseDto saveAppoiment(AppoimentCreateDto a);
    AppoimentResponseDto updateAppoiment(Long id, AppoimentUpdateDto a);
    AppoimentDetailDto findByID(Long id);
    Boolean validarMesActual(LocalDateTime mesCita);
    ApiResponse<Void> deleteAppoiment(Long id);
}
