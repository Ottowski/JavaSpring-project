package com.example.individuellUppgift2.Repository;

import com.example.individuellUppgift2.AppEntity.AppFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<AppFile, Long> {
    List<AppFile> findByFilenameAndFolderName(String filename, String folderName);

    List<AppFile> findByUsernameAndFolderName(String username, String folderName);
    // Add more custom queries if needed
}
