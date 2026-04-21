package com.mycompany.mavenproject.mapper;

import com.mycompany.mavenproject.model.ErrorResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Catch-all exception mapper for unexpected server errors.
 * 
 * @author methdul
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    
    @Override
    public Response toResponse(Throwable exception) {
        ErrorResponse error = new ErrorResponse(
                500,
                "Unexpected Server Error",
                "An unexpected error occurred. Please contact the campus admin for support."
        );
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
