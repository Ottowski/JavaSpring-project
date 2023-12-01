package com.example.individuellUppgift2.DTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class FolderDTO {
    @Getter
    private String folderName;

    public FolderDTO() {

    }

    private List<String> files;

    // Constructors, getters, and setters
}
