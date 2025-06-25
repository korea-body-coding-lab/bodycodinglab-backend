package com.project.bcl_back.service;

import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.dto.FileResponseDto;
import com.project.bcl_back.entity.UploadFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface UploadFileService {
    UploadFile saveFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException;
    UploadFile updateFile(Long targetId, TargetType targetType, MultipartFile newFile) throws IOException;
//    void deleteFile(Long targetId, TargetType targetType) throws IOException;
    UploadFile findByTargetIdAndTargetType(Long targetId, TargetType targetType);

    List<FileResponseDto> uploadMultiple(List<MultipartFile> files, Long targetId, TargetType targetType);

    List<FileResponseDto> getFileList(Long targetId, TargetType targetType);

    FileResponseDto getFile(Long fileId);

    List<FileResponseDto> replaceFiles(Long targetId, TargetType targetType, List<Long> keepIds, List<MultipartFile> newFiles);

    void deleteFile(Long fileId);
}
