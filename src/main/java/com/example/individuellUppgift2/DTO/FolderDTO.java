package com.example.individuellUppgift2.DTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class FolderDTO {
    private String folderName;
    public String getFolderName() {
        return folderName;
    }
    private List<String> files;

    // Constructors, getters, and setters
}
