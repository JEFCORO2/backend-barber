package com.jcoronel.api.rest.barber.backend_barber.dto;

import java.util.List;
import java.util.Map;

public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private List<T> listGeneric;
    private Map<String, String> errors;
    
    public ApiResponse() {
    }

    public List<T> getListGeneric() {
        return listGeneric;
    }

    public void setListGeneric(List<T> listGeneric) {
        this.listGeneric = listGeneric;
    }

    public ApiResponse(int status, String message, List<T> listGeneric) {
        this.status = status;
        this.message = message;
        this.listGeneric = listGeneric;
    }

    public ApiResponse(int status, String message, T data, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
