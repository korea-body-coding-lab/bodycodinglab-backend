package com.project.bcl_back.dto;

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
}
