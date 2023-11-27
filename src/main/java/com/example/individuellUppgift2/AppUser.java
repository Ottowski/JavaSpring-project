package com.example.individuellUppgift2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_user") // Change the table name
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

