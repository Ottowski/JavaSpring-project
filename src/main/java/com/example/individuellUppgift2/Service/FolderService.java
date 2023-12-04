package com.example.individuellUppgift2.Service;

import com.example.individuellUppgift2.AppEntity.AppFolder;
import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.Repository.AppFolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FolderService {
    private final AppFolderRepository folderRepository;
    private final FileService fileService;

    @Autowired
    public FolderService(AppFolderRepository folderRepository, FileService fileService) {
        this.folderRepository = folderRepository;
        this.fileService = fileService;
    }
    public void createFolder(Long userId) {
        AppFolder folder = new AppFolder();
        String folderName = ""; // You should set the actual folder name
        folder.setFolderName(folderName);
        folder.setUsername(String.valueOf(userId));
        folderRepository.save(folder);
        System.out.println("Folder created: " + folderName + " for user: " + userId);
    }

    public List<FolderDTO> getAllFolders(String username) {
        List<AppFolder> folders = folderRepository.findByUsername(username);
        List<FolderDTO> folderDTOs = new ArrayList<>();

        for (AppFolder folder : folders) {
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderName(folder.getFolderName());

            // Fetch file names associated with the folder
            List<String> fileNames = fileService.getFilesInFolder(username, folder.getFolderName());
            folderDTO.setFiles(fileNames);

            folderDTOs.add(folderDTO);
        }

        return folderDTOs;
    }
}
