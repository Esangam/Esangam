package org.esangam.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.esangam.dto.ErrorResponse;

@Provider
public class MemberAlreadyExistsExceptionMapper implements ExceptionMapper<MemberAlreadyExistsException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(MemberAlreadyExistsException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                Response.Status.CONFLICT.getStatusCode(),
                "Conflict",
                uriInfo != null ? uriInfo.getPath() : null
        );

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
