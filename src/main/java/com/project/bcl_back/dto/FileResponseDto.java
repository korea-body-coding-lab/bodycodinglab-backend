package com.project.bcl_back.dto;

import com.project.bcl_back.entity.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDto {
    private String originalName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;

    public static FileResponseDto fromEntity(UploadFile file) {
        return new FileResponseDto(
                file.getOriginalName(),
                file.getFileName(),
                file.getFilePath(),
                file.getFileType(),
                file.getFileSize()
        );
    }
}
