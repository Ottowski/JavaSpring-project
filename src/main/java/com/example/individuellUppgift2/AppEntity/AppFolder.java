package com.example.individuellUppgift2.AppEntity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "folders")
@Getter
@Setter
public class AppFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String folderName;

    @Column(nullable = false)
    private String username;
}

