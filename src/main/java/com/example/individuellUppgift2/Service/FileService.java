package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppFile;
import com.example.individuellUppgift2.Repository.AppFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class FileService {
    private final AppFileRepository appFileRepository;
    @Autowired
    public FileService(AppFileRepository appFileRepository) {
        this.appFileRepository = appFileRepository;
    }
    public String uploadFile(MultipartFile file, String username, String folderName) {
        // Check if the file already exists in the folder
        if (fileExists(username, folderName, file.getOriginalFilename())) {
            return "File already exists in the folder: " + file.getOriginalFilename();
        }

        // Your existing logic for uploading the file goes here
        // ...

        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    private boolean fileExists(String username, String folderName, String filename) {
        List<AppFile> files = appFileRepository.findByUsernameAndFolderNameAndFilename(username, folderName, filename);
        return !files.isEmpty();
    }
    public List<String> getFilesInFolder(String username, String folderName) {
        List<String> fileNames = new ArrayList<>();
        List<AppFile> files = appFileRepository.findByUsernameAndFolderName(username, folderName);
        for (AppFile file : files) {
            fileNames.add(file.getFilename());
        }
        return fileNames;
    }
}