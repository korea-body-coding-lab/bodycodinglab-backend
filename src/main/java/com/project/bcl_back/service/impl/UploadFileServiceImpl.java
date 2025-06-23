package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.repository.UploadFileRepository;
import com.project.bcl_back.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
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
    public UploadFile saveFile(MultipartFile file, Long targetId, TargetType targetType) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String original = file.getOriginalFilename();
            String uuidName = UUID.randomUUID() + "_" + original;
            String fullPath = uploadDir + "/" + uuidName;
            file.transferTo(new File(fullPath));

            UploadFile uf = UploadFile.builder()
                    .originalName(original)
                    .fileName(uuidName)
                    .filePath(uploadDir + "/")
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .targetId(targetId)
                    .targetType(targetType)
                    .build();
            uploadFileRepository.save(uf);

            return uf;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    @Transactional
    public UploadFile updateFile(Long targetId, TargetType targetType, MultipartFile newFile) throws IOException {
        UploadFile existing = uploadFileRepository.findByTargetIdAndTargetType(targetId, targetType);

        if (existing == null) {
            return saveFile(newFile, targetId, targetType);
        }

        Path oldPath = Paths.get(existing.getFilePath() + existing.getFileName());
        Files.deleteIfExists(oldPath);

        String newFileName = UUID.randomUUID() + "_" + newFile.getOriginalFilename();
        String savePath = existing.getFilePath();
        Files.copy(newFile.getInputStream(), Paths.get(savePath, newFileName));

        existing.updateFile(
                newFile.getOriginalFilename(),
                newFileName,
                newFile.getContentType(),
                newFile.getSize()
        );

        return existing;
    }

    @Override
    public void deleteFile(Long targetId, TargetType targetType) throws IOException {
        UploadFile existing = uploadFileRepository.findByTargetIdAndTargetType(targetId, targetType);

        if (existing == null) {
            throw new FileNotFoundException(ResponseMessage.FILE_NOT_FOUND);
        }

        Path oldPath = Paths.get(existing.getFilePath() + existing.getFileName());
        Files.deleteIfExists(oldPath);

        uploadFileRepository.delete(existing);
    }

    @Override
    public UploadFile findByTargetIdAndTargetType(Long targetId, TargetType targetType) {
        return uploadFileRepository.findByTargetIdAndTargetType(targetId, targetType);
    }
}
