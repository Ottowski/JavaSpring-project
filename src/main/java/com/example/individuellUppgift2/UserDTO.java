package com.example.individuellUppgift2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String username;
    private String password;

    public UserDTO() {
    }

    public UserDTO(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDTO(int id, String userNotFound) {
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

