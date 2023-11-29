package com.example.individuellUppgift2.Service;

import com.example.individuellUppgift2.AppEntity.AppFolder;
import com.example.individuellUppgift2.Repository.AppFolderRepository;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

    private final AppFolderRepository folderRepository;

    public FolderService(AppFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public void createFolder(String folderName) {
        AppFolder folder = new AppFolder();
        folder.setFolderName(folderName);
        folderRepository.save(folder);
        System.out.println("Folder created: " + folderName);
    }
    public void createFolder(String username, Object folderName) {
        System.out.println("Creating folder for user " + username + ": " + folderName);
    }
    // Implement folder-related business logic, e.g., createFolder, deleteFolder, etc.
}

