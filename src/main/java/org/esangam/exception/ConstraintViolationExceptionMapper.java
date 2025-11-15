package org.esangam.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.esangam.dto.ErrorResponse;

import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        String details = exception.getConstraintViolations()
                .stream()
                .map(this::formatViolation)
                .collect(Collectors.joining("; "));

        ErrorResponse error = new ErrorResponse(
                details,
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Bad Request",
                uriInfo != null ? uriInfo.getPath() : null
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }

    private String formatViolation(ConstraintViolation<?> v) {
        String field = v.getPropertyPath() != null ? v.getPropertyPath().toString() : "";
        return field + ": " + v.getMessage();
    }
}
