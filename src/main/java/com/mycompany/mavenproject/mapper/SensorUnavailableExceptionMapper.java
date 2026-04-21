package com.mycompany.mavenproject.mapper;

import com.mycompany.mavenproject.exception.SensorUnavailableException;
import com.mycompany.mavenproject.model.ErrorResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Filter for sensors that are in MAINTENANCE mode.
 * Returns HTTP 403 Forbidden to prevent data corruption.
 * 
 * @author methdul
 */
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    
    @Override
    public Response toResponse(SensorUnavailableException exception) {
        ErrorResponse error = new ErrorResponse(
                403,
                "Sensor Unavailable",
                exception.getMessage()
        );
        
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
