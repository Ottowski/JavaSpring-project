package com.example.individuellUppgift2.Repository;
import com.example.individuellUppgift2.AppEntity.AppFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppFolderRepository extends JpaRepository<AppFolder, Long> {
    List<AppFolder> findByUsername(String username);
}