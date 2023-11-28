package com.example.individuellUppgift2.Service;

// StorageService.java

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class StorageService {

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    public void uploadFile(MultipartFile file, String folderName) throws IOException {
        // Create a directory for each user
        Path userDir = Paths.get(uploadDir, folderName);
        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
        }

        // Extract the filename from the file path
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Copy file to the target location
        Path targetLocation = userDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);
    }

    public byte[] downloadFile(String folderName, String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, folderName, fileName);
        return Files.readAllBytes(filePath);
    }

    public void deleteFile(String folderName, String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, folderName, fileName);
        Files.delete(filePath);
    }
}
