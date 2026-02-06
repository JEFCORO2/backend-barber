package com.jcoronel.api.rest.barber.backend_barber.interceptors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("idInterceptor")
public class IdValidationInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String path = request.getRequestURI();
        String[] segments = path.split("/");

        if (segments.length > 0) {
            String idString = segments[segments.length-1];

            try {
                Long.parseLong(idString);
                return true;
            } catch (NumberFormatException e) {
                //Tomamos la ruta
                Map<String, String> json = new HashMap<>();
                json.put("error", "El id " + idString + " no es un entero, escribe un entero");
    
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(json);
                response.setContentType("application/json");
                response.setStatus(401);
                response.getWriter().write(jsonString);
    
                return false;
            }
        }

        return true;
    }
}
