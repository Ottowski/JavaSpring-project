package com.example.individuellUppgift2.AppEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "file")
public class AppFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String filename;
}