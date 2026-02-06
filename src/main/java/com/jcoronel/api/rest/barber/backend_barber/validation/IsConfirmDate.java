package com.jcoronel.api.rest.barber.backend_barber.validation;

import jakarta.validation.Payload;


public @interface IsConfirmDate {
    String message() default "{fecha fuera del horario de atencion}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
