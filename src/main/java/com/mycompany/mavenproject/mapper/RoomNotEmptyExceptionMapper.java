package com.mycompany.mavenproject.mapper;

import com.mycompany.mavenproject.exception.RoomNotEmptyException;
import com.mycompany.mavenproject.model.ErrorResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper handled when attempting to delete a room that still contains sensors.
 * Returns an HTTP 409 Conflict status.
 * 
 * @author methdul
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        ErrorResponse error = new ErrorResponse(
                409,
                "Resource Conflict",
                exception.getMessage()
        );

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
