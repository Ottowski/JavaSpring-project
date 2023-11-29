package com.example.individuellUppgift2.Repository;

import com.example.individuellUppgift2.AppEntity.AppFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AppFolderRepository extends JpaRepository<AppFolder, Long> {
}
