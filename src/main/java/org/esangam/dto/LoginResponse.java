package org.esangam.dto;

/** DTO returned after successful login. */
public class LoginResponse {
    public String token;
    public LoginResponse(String token) { this.token = token; }
}
