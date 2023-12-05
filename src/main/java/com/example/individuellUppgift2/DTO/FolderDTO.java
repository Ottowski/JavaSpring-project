package com.example.individuellUppgift2.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderDTO {
    private Long folderId;
    private String folderName;
    private List<String> files;
    private String username; // Add this field

    public FolderDTO() {
    }

    public FolderDTO(Long folderId, String folderName, List<String> files, String username) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.files = files;
        this.username = username; // Initialize the new field
    }

}
