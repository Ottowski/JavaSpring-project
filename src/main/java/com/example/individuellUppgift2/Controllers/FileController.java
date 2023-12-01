package com.example.individuellUppgift2.Controllers;

import com.example.individuellUppgift2.Service.FileService;
import com.example.individuellUppgift2.Service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final FolderService folderService;

    private static final String UPLOAD_DIR = "uploads/";
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(FileService fileService, FolderService folderService) {
        this.fileService = fileService;
        this.folderService = folderService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName) {
        if (file.isEmpty()) {
            logger.info("File has not been selected");
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // Create the upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            logger.info("Received file upload request. File name: {}", file.getOriginalFilename());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Create the folder using folderService
            folderService.createFolder(folderName);

            // Define the folder and file paths
            String folderPath = UPLOAD_DIR + folderName + "/";
            String filePath = folderPath + file.getOriginalFilename();

            // Save the file to the server
            Path folderPathObj = Paths.get(folderPath);
            Path filePathObj = Paths.get(filePath);

            if (!Files.exists(folderPathObj)) {
                Files.createDirectories(folderPathObj);
            }

            Files.write(filePathObj, file.getBytes());

            // Call the fileService to handle the file upload logic
            String response = fileService.uploadFile(file);

            logger.info("File upload successful. File name: {}", file.getOriginalFilename());
            return ResponseEntity.ok("File upload successful. File name: " + file.getOriginalFilename());
        } catch (IOException e) {
            logger.error("File upload failed. Exception: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("File upload failed. Please try again.");
        }
    }
}
