package com.example.individuellUppgift2.Controllers;

import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // Endpoint for creating a new folder. http://localhost:8080/api/folders/createFolder
    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO) {
        // Get the principal from the authentication context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        // Check if the principal is an instance of UserDetails
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            // If not UserDetails, check if it's a String
            username = (String) principal;
        } else {
            // Handle the case where principal is neither UserDetails nor String
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }

        // Call the folder service to create a new folder for the user
        folderService.createFolder(username);

        return ResponseEntity.ok("Folder created successfully");
    }


}

