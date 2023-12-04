package com.example.individuellUppgift2.AppEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_file")
public class AppFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private AppFolder folder;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String folderName;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Add more fields and relationships as needed

    // Constructors, getters, setters, and other methods can be added here
}

