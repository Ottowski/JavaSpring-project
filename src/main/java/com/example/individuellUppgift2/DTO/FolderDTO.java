package com.example.individuellUppgift2.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderDTO {
    private String folderId;
    private String folderName;
    private List<String> files;

    public FolderDTO() {
    }

    public FolderDTO(String folderId, String folderName, List<String> files) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.files = files;
    }
}
