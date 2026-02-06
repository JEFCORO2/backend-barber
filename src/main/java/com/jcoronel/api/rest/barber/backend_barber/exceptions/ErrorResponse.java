package com.jcoronel.api.rest.barber.backend_barber.exceptions;

import java.util.Map;

//Record mejor para evitar getters and setters
public record ErrorResponse(Map<String,String> error) {
}
