package com.example.individuellUppgift2.Controllers;

import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // Endpoint for creating a new folder. http://localhost:8080/api/folders/create
    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO) {
        // Get the username of the authenticated user
        // For simplicity, assuming you have a logged-in user

        // Call the folder service to create a new folder for the user
        folderService.createFolder("username", folderDTO.getFolderName());

        return ResponseEntity.ok("Folder created successfully");
    }
}
