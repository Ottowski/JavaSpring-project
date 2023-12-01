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

    @Autowired
    public FolderService(AppFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public void createFolder(String username) {
        AppFolder folder = new AppFolder();
        String folderName = ""; // You should set the actual folder name
        folder.setFolderName(folderName);
        folder.setUsername(username);
        folderRepository.save(folder);
        System.out.println("Folder created: " + folderName + " for user: " + username);
    }

    public List<FolderDTO> getAllFolders(String username) {
        List<AppFolder> folders = folderRepository.findByUsername(username);
        List<FolderDTO> folderDTOs = new ArrayList<>();

        for (AppFolder folder : folders) {
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderName(folder.getFolderName());
            // You may want to set other properties as needed
            folderDTOs.add(folderDTO);
        }

        return folderDTOs;
    }
}
