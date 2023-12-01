package com.example.individuellUppgift2.Controllers;
import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Service.FolderService;
import com.example.individuellUppgift2.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;
import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class FolderController {
    private final FolderService folderService;
    private final FileService fileService;
    private static final Logger LOGGER = Logger.getLogger(FolderController.class.getName());

    @Autowired
    public FolderController(FolderService folderService, FileService fileService) {
        this.folderService = folderService;
        this.fileService = fileService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFoldersWithFiles() {
        String username = getUsernameFromAuthentication();
        LOGGER.info("Retrieving folders for user: " + username);

        try {
            List<FolderDTO> folders = folderService.getAllFolders(username);

            if (folders != null) {
                // ... (rest of the code)
                return ResponseEntity.ok(folders);
            } else {
                LOGGER.warning("Failed to retrieve folders for user: " + username);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to retrieve folders for user: " + username);
            }
        } catch (Exception e) {
            LOGGER.severe("An error occurred while processing the request: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        } finally {
            LOGGER.info("Request processing completed.");
        }
    }


    private String getUsernameFromAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }

        return username;
    }

    // Endpoint for creating a new folder. http://localhost:8080/api/folders/createFolder
    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO) {
        try {
            String username = getUsernameFromAuthentication();
            folderService.createFolder(username);
            return ResponseEntity.ok("Folder created successfully");
        } catch (Exception e) {
            LOGGER.severe("An error occurred while creating the folder: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the folder: " + e.getMessage());
        }
    }
}