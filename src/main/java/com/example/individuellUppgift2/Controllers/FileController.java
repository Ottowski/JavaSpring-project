package com.example.individuellUppgift2.Controllers;
import com.example.individuellUppgift2.Service.FileService;
import com.example.individuellUppgift2.Service.FolderService;
import com.example.individuellUppgift2.DTO.FolderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    //Endpoint for uploading a file in a folder: http://localhost:8080/api/files/upload
    // from-data Key: folderName, Value: "The folder name", Key: file (make sure its set on "file"), Value: "name of file"
    // Auhtorization (Bearer Token) jwt token needed from user login
    // ...

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("folderName") String folderName,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("File has not been selected");
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        try {
            String username = getUsernameFromAuthentication();

            // Check if the folder exists
            if (!folderService.folderExists(username, folderName)) {
                return ResponseEntity.badRequest().body("Folder does not exist: " + folderName);
            }

            String folderPath = UPLOAD_DIR + username + "/";
            String filePath = folderPath + file.getOriginalFilename();

            // Save the file to the server
            Path folderPathObj = Paths.get(folderPath);
            Path filePathObj = Paths.get(filePath);
            if (!Files.exists(folderPathObj)) {
                Files.createDirectories(folderPathObj);
            }
            Files.write(filePathObj, file.getBytes());

            // Call the fileService to handle the file upload logic
            String response = fileService.uploadFile(file, username, folderName);
            logger.info("File upload successful. File name: {}", file.getOriginalFilename());
            return ResponseEntity.ok("File upload successful. File name: " + file.getOriginalFilename());
        } catch (IOException e) {
            logger.error("File upload failed. Exception: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("File upload failed. Please try again.");
        }
    }
    // Endpoint for downloading a file: http://localhost:8082/api/files/download
    // Don't forget you have to have done the upload method first!
    // Params Key: Key: file (make sure its set on "file"), Value: "name of file"'
    // Auhtorization (Bearer Token) jwt token needed from user login
    @GetMapping("/download")
    public <SpringResource> ResponseEntity<Object> downloadFile(@RequestParam("filename") String filename) {
        try {
            String username = getUsernameFromAuthentication();
            String folderPath = UPLOAD_DIR + username + "/";
            String filePath = folderPath + filename;
            logger.info("File path: {}", filePath);
            SpringResource resource = (SpringResource) new UrlResource(Paths.get(filePath).toUri());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            logger.info("File downloaded successfully. File name: {}", filename);
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (Exception e) {
            // Handle file not found or other exceptions
            logger.error("Error during file download: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    // Endpoint for deleting a file: http://localhost:8082/api/files/delete
    // Don't forget you have to have done the download method first!
    // Params Key: Key: file (make sure its set on "file"), Value: "name of file"'
    // Auhtorization (Bearer Token) jwt token needed from user login
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) {
        try {
            String username = getUsernameFromAuthentication();
            String folderPath = UPLOAD_DIR + username + "/";
            String filePath = folderPath + filename;
            Path filePathObj = Paths.get(filePath);
            if (Files.exists(filePathObj)) {
                Files.delete(filePathObj);
                logger.info("File deleted successfully. File name: {}", filename);
                return ResponseEntity.ok("File deleted successfully. File name: " + filename);
            } else {
                logger.info("File not found. File name: {}", filename);
                return ResponseEntity.status(404).body("File not found. File name: " + filename);
            }
        } catch (IOException e) {
            logger.error("File deletion failed. Exception: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("File deletion failed. Please try again.");
        }
    }
    private String getUsernameFromAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }
    }
}
