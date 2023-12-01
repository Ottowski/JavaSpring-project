package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppFolder;
import com.example.individuellUppgift2.Repository.AppFolderRepository;
import org.springframework.stereotype.Service;
@Service
public class FolderService {
    private final AppFolderRepository folderRepository;
    public FolderService(AppFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }
    public void createFolder(String username) {
        AppFolder folder = new AppFolder();
        String folderName = "";
        folder.setFolderName(folderName);
        folder.setUsername(username);  // Set the username for the folder
        folderRepository.save(folder);
        System.out.println("Folder created: " + folderName + " for user: " + username);
    }
}