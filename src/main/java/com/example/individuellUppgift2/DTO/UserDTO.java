package com.example.individuellUppgift2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    // User ID.
    private int id;

    // User username.
    private String username;

    // User password.
    private String password;

    // Default constructor.
    public UserDTO() {}

    // Constructor with ID, username, and password.
    public UserDTO(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Constructor with ID and a message for user not found.
    public UserDTO(int id, String userNotFound) {}

    // Override toString method for UserDTO.
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
