package com.jcoronel.api.rest.barber.backend_barber.repositories;

//import java.time.LocalDate;
//import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jcoronel.api.rest.barber.backend_barber.entities.Appoiment;
//import com.jcoronel.api.rest.barber.backend_barber.entities.AppoimentService;

public interface AppoimentRepository extends CrudRepository<Appoiment, Long>{
    @Query("SELECT a FROM Appoiment a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.appoimentService aps LEFT JOIN FETCH aps.service")
    List<Appoiment> findAllAppoimentsTodo();

    @Query("SELECT a FROM Appoiment a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.appoimentService aps LEFT JOIN FETCH aps.service WHERE a.id = ?1")
    Optional<Appoiment> findDetailedById(Long id);
}
