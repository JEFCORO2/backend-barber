package com.jcoronel.api.rest.barber.backend_barber.exceptions;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message){
        super(message);
    }
}
