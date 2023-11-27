package com.example.individuellUppgift2;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID id;
    private String username;
    private String password;

    // Add a default constructor
    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(String errorMessage) {
        this.username = errorMessage;
    }
}
