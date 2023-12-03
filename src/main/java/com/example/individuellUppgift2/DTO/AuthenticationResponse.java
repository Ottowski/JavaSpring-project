package com.example.individuellUppgift2.DTO;
import lombok.Data;
@Data
public class AuthenticationResponse {
    private final String jwt;
    private final UserDTO user;
    public AuthenticationResponse(String jwt, UserDTO user) {
        this.jwt = jwt;
        this.user = user;
    }
}