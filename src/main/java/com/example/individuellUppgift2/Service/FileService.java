package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppFile;
import com.example.individuellUppgift2.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    public String uploadFile(MultipartFile file) {
        //fileRepository.save(new AppFile(file.getOriginalFilename()));

        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    public List<String> getFilesInFolder(String username, String folderName) {
        List<String> fileNames = new ArrayList<>();

        // Implement the logic to fetch file names associated with the folder
        // For example, you can query the database or use another method to retrieve file names.

        // Assuming you have a fileRepository method to find files by username and folderName
        List<AppFile> files = fileRepository.findByUsernameAndFolderName(username, folderName);

        for (AppFile file : files) {
            fileNames.add(file.getFilename());
        }

        return fileNames;
    }
}