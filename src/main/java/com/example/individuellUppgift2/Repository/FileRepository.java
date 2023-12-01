package com.example.individuellUppgift2.Repository;

import com.example.individuellUppgift2.AppEntity.AppFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<AppFile, Long> {
    // Add custom queries if needed
}
