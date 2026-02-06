package com.jcoronel.api.rest.barber.backend_barber.validation;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsConfirmDateValidation implements ConstraintValidator<IsConfirmDate, LocalDateTime>{

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {

        
        return true;
    }

}
