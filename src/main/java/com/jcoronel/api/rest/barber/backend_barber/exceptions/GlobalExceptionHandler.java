package com.jcoronel.api.rest.barber.backend_barber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MonthCurrentException.class)
    public ResponseEntity<ErrorResponse> handleException(MonthCurrentException e){
        Map<String, String> errors = new HashMap<String,String>();

        String fieldName = "month";
        String errorMessage = e.getMessage();
        errors.put(fieldName, errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(ScheduleAtentionException.class)
    public ResponseEntity<ErrorResponse> handleException(ScheduleAtentionException e){
        Map<String, String> errors = new HashMap<String,String>();

        String fieldName = "time";
        String errorMessage = e.getMessage();
        errors.put(fieldName, errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException e){
        Map<String, String> errors = new HashMap<String,String>();

        String fieldName = e.getResourceName().toLowerCase();
        String errorMessage = e.getMessage();
        errors.put(fieldName, errorMessage);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<String,String>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        Map<String, String> errors = new HashMap<String,String>();
        String fieldName = "message";
        String errorMessage = "An error has occurred. Please contact the administrator or try again later";
        errors.put(fieldName, errorMessage);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errors));
    }
}
