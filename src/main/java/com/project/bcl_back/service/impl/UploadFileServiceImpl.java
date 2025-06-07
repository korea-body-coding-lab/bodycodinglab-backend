package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.repository.UploadFileRepository;
import com.project.bcl_back.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public UploadFile saveFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String uuidName = UUID.randomUUID() + "_" + original;
        String fullPath = uploadDir + "/" + uuidName;
        file.transferTo(new File(fullPath));

        UploadFile uf = UploadFile.builder()
                .originalName(original)
                .fileName(uuidName)
                .filePath("/file/" + uuidName)
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .targetId(targetId)
                .targetType(targetType)
                .build();
        uploadFileRepository.save(uf);

        return uf;
    }

    @Override
    public byte[] getProfileImage(Long targetId, TargetType targetType) throws IOException {
        UploadFile uf = uploadFileRepository.findByTargetIdAndTargetType(targetId, targetType);
        Path path = Paths.get("C:/upload" + uf.getFilePath());
        return Files.readAllBytes(path);
    }
}
