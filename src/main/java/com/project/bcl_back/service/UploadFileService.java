package com.project.bcl_back.service;

import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.entity.UploadFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UploadFileService {
    UploadFile saveFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException;
    UploadFile updateFile(Long targetId, TargetType targetType, MultipartFile newFile) throws IOException;
    void deleteFile(Long targetId, TargetType targetType) throws IOException;
    UploadFile findByTargetIdAndTargetType(Long targetId, TargetType targetType);
}
