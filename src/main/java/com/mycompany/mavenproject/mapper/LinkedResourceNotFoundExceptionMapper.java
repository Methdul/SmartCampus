package com.mycompany.mavenproject.mapper;

import com.mycompany.mavenproject.exception.LinkedResourceNotFoundException;
import com.mycompany.mavenproject.model.ErrorResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Handles cases where a referenced resource (like a RoomId in a Sensor) does not exist.
 * Maps to HTTP 422 Unprocessable Entity.
 * 
 * @author methdul
 */
@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    
    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
            422,
            "Integrity Validation Failed",
            exception.getMessage()
        );
        
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
