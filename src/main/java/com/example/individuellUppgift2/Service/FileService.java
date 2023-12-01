package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppFile;
import com.example.individuellUppgift2.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        return null;
    }
}