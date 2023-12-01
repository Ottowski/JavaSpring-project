package com.example.individuellUppgift2.Service;

import com.example.individuellUppgift2.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String uploadFile(MultipartFile file) {
        // Implement file upload logic and save to the repository
        // You can also map the DTO to the entity here

        // Example: Save file entity to the repository
        // fileRepository.save(new AppFile(file.getOriginalFilename()));

        return "File uploaded successfully: " + file.getOriginalFilename();
    }
}
