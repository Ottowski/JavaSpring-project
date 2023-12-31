package com.example.individuellUppgift2.Controllers;
import com.example.individuellUppgift2.AppEntity.AppFolder;
import com.example.individuellUppgift2.AppEntity.AppFile;
import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Repository.AppFolderRepository;
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
    // Endpoint for creating a new folder. http://localhost:8082/api/folders/createFolder
    // Auhtorization (Bearer Token) jwt token needed from user login
    // raw (JSON):
    // {
    //    "folderName": "NyFolder"
    // }
    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO) {
        try {
            String username = getUsernameFromAuthentication();
            folderService.createFolder(username, folderDTO);
            return ResponseEntity.ok("Folder created successfully");
        } catch (Exception e) {
            LOGGER.severe("An error occurred while creating the folder: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the folder: " + e.getMessage());
        }
    }
    // Endpoint to get a list of all folders. http://localhost:8082/api/folders/all
    // Auhtorization (Bearer Token) jwt token needed from user login
    @GetMapping("/all")
    public ResponseEntity<List<FolderDTO>> getAllFoldersWithFiles() {
        String username = getUsernameFromAuthentication();
        LOGGER.info("Retrieving folders for user: " + username);
        List<AppFolder> folders = folderRepository.findByUsername(username);
        List<FolderDTO> folderDTOs = folders.stream()
                .map(folder -> {
                    FolderDTO folderDTO = new FolderDTO();
                    folderDTO.setFolderId(folder.getFolderId());
                    folderDTO.setFolderName(folder.getFolderName());
                    folderDTO.setUsername(username);
                    // Add logging statement
                    LOGGER.info("Fetching files for folder: " + folder.getFolderName());
                    List<String> fileNames = folder.getFiles().stream()
                            .map(AppFile::getFilename)
                            .toList();
                    folderDTO.setFiles(fileNames);
                    folder.getFiles().size(); // Ensure files are initialized
                    return folderDTO;
                })
                .toList();

        return ResponseEntity.ok(folderDTOs);
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
}