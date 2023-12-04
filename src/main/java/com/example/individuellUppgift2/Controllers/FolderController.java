package com.example.individuellUppgift2.Controllers;
import com.example.individuellUppgift2.AppEntity.AppFolder;
import com.example.individuellUppgift2.AppEntity.AppFile;
import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Repository.AppFolderRepository;
import com.example.individuellUppgift2.Service.FolderService;
import com.example.individuellUppgift2.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private final AppFolderRepository folderRepository;
    private final FolderService folderService;
    private final FileService fileService;
    private static final Logger LOGGER = Logger.getLogger(FolderController.class.getName());

    @Autowired
    public FolderController(AppFolderRepository folderRepository, FolderService folderService, FileService fileService) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
        this.fileService = fileService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FolderDTO>> getAllFoldersWithFiles(Authentication authentication) {
        String userId = getUserIdFromAuthentication(authentication);
        LOGGER.info("Retrieving folders for user: " + userId);

        // Retrieve all folders for the specific user
        List<AppFolder> folders = folderRepository.findByUsername(userId);

        // Map AppFolder entities to FolderDTO objects
        List<FolderDTO> folderDTOs = folders.stream()
                .map(folder -> {
                    FolderDTO folderDTO = new FolderDTO();
                    folderDTO.setFolderName(folder.getFolderName());

                    // Assuming you have a method to get file names from AppFolder entity
                    List<String> fileNames = folder.getFiles().stream()
                            .map(AppFile::getFilename)
                            .toList();

                    folderDTO.setFiles(fileNames);
                    return folderDTO;
                })
                .toList();

        return ResponseEntity.ok(folderDTOs);
    }

    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO, Authentication authentication) {
        try {
            // Updated logic to handle null authentication
            if (authentication == null || authentication.getPrincipal() == null) {
                // Handle the case when authentication is null or principal is null,
                // for example, return an error response
                return new ResponseEntity<>("Authentication is null", HttpStatus.UNAUTHORIZED);
            }

            String userId = getUserIdFromAuthentication(authentication);
            folderService.createFolder(Long.valueOf(userId));
            return new ResponseEntity<>("Folder created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.severe("An error occurred while creating the folder: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the folder: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Inside FolderController and FileController
    private String getUserIdFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            // Retrieve the user ID from the AppUser entity
            if (userDetails instanceof AppUser) {
                return ((AppUser) userDetails).getUserId();
            } else {
                // Handle if UserDetails is not an instance of AppUser
                throw new IllegalStateException("Unexpected UserDetails type: " + userDetails.getClass());
            }
        } else if (principal instanceof String) {
            // Handle if the principal is a string
            // You may need to adjust this based on your authentication setup
            return (String) principal;
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }
    }



}