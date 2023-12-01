package com.example.individuellUppgift2.AppEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_user")
public class AppUser {

    // User ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // User username.
    @Column(name = "username", unique = true)
    private String username;

    // User password.
    @Column(name = "password")
    private String password;

    // Constructor with username and password.
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}