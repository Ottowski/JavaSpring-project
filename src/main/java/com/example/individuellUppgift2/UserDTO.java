package com.example.individuellUppgift2;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
