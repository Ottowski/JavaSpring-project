package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppFolder;
import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Repository.AppFolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FolderService {
    private final AppFolderRepository folderRepository;
    private final FileService fileService;
    @Autowired
    public FolderService(AppFolderRepository folderRepository, FileService fileService) {
        this.folderRepository = folderRepository;
        this.fileService = fileService;
    }
    public void createFolder(String username, FolderDTO folderDTO) {
        AppFolder folder = new AppFolder();
        Long folderId = generateUniqueFolderId(); // Change the type to Long
        folder.setFolderId(folderId);

        // Set the folder name from the folderDTO
        String folderName = folderDTO.getFolderName();
        folder.setFolderName(folderName);

        folder.setUsername(username);
        folderRepository.save(folder);
        System.out.println("Folder created: " + folderName + " for user: " + username);
    }
    public boolean folderExists(String username, String folderName) {
        List<AppFolder> folders = folderRepository.findByUsernameAndFolderName(username, folderName);
        return !folders.isEmpty();
    }

    private Long generateUniqueFolderId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
    public List<FolderDTO> getAllFolders(String username) {
        List<AppFolder> folders = folderRepository.findByUsername(username);
        List<FolderDTO> folderDTOs = new ArrayList<>();
        for (AppFolder folder : folders) {
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderId(folder.getFolderId());
            folderDTO.setFolderName(folder.getFolderName());
            folderDTO.setUsername(username);
            List<String> fileNames = fileService.getFilesInFolder(username, folder.getFolderName());
            folderDTO.setFiles(fileNames);
            folderDTOs.add(folderDTO);
        }
        return folderDTOs;
    }
}
