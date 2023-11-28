package com.example.individuellUppgift2.Controllers;

// FileController.java

import com.example.individuellUppgift2.DTO.FileDTO;
import com.example.individuellUppgift2.Service.FileService;
import com.example.individuellUppgift2.Service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final StorageService storageService;

    public FileController(FileService fileService, StorageService storageService) {
        this.storageService = storageService;
    }

    // Endpoint for user folder. http://localhost:8080/api/files/upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestBody FileDTO fileDTO) {
        // Assume folderName is provided in the FileDTO
        String folderName = fileDTO.getFolderName();

        // Upload the file to the specified folder
        try {
            storageService.uploadFile(file, folderName);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file");
        }
    }

    // Endpoint for user folder. http://localhost:8080/api/files/download
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestBody FileDTO fileDTO) {
        // Assume folderName and fileName are provided in the FileDTO
        String folderName = fileDTO.getFolderName();
        String fileName = fileDTO.getFileName();

        // Download the file from the specified folder
        try {
            byte[] fileContent = storageService.downloadFile(folderName, fileName);
            return ResponseEntity.ok(fileContent);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to download file".getBytes());
        }
    }

    // Endpoint for user folder. http://localhost:8080/api/files/delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestBody FileDTO fileDTO) {
        // Assume folderName and fileName are provided in the FileDTO
        String folderName = fileDTO.getFolderName();
        String fileName = fileDTO.getFileName();

        // Delete the file from the specified folder
        try {
            storageService.deleteFile(folderName, fileName);
            return ResponseEntity.ok("File deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to delete file");
        }
    }
}
