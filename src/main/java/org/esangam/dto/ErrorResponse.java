package org.esangam.dto;

import java.time.Instant;

public class ErrorResponse {

    private String message;
    private int status;
    private String error;
    private String path;
    private Instant timestamp = Instant.now();

    public ErrorResponse() {}

    public ErrorResponse(String message, int status, String error, String path) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
