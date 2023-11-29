package com.example.individuellUppgift2.Service;

import org.springframework.stereotype.Service;

@Service
public class FolderService {
    public void createFolder(String username, Object folderName) {
        System.out.println("Creating folder for user " + username + ": " + folderName);
    }
    // Implement folder-related business logic, e.g., createFolder, deleteFolder, etc.
}

