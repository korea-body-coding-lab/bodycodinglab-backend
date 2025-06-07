package com.project.bcl_back.service;

import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    UploadFile saveFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException;
    byte[] getProfileImage(Long targetId, TargetType targetType) throws IOException;
}
