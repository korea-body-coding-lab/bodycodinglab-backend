package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.dto.FileResponseDto;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.repository.UploadFileRepository;
import com.project.bcl_back.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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

//    @Override
//    public void deleteFile(Long targetId, TargetType targetType) throws IOException {
//        UploadFile existing = uploadFileRepository.findByTargetIdAndTargetType(targetId, targetType);
//
//        if (existing == null) {
//            throw new FileNotFoundException(ResponseMessage.FILE_NOT_FOUND);
//        }
//
//        Path oldPath = Paths.get(existing.getFilePath() + existing.getFileName());
//        Files.deleteIfExists(oldPath);
//
//        uploadFileRepository.delete(existing);
//    }

    @Override
    public UploadFile findByTargetIdAndTargetType(Long targetId, TargetType targetType) {
        return uploadFileRepository.findByTargetIdAndTargetType(targetId, targetType);
    }

    @Override
    @Transactional
    public List<FileResponseDto> uploadMultiple(List<MultipartFile> files, Long targetId, TargetType targetType) {
        List<UploadFile> savedEntities = new ArrayList<>();

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IllegalStateException("업로드 디렉토리를 생성할 수 없습니다.");
            }
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            try {
                String original = file.getOriginalFilename();
                String uuidName = UUID.randomUUID() + "_" + original;
                String fullPath = uploadDir + File.separator + uuidName;

                file.transferTo(new File(fullPath));

                UploadFile uploadFile = UploadFile.builder()
                        .originalName(original)
                        .fileName(uuidName)
                        .filePath(uploadDir + File.separator)
                        .fileSize(file.getSize())
                        .fileType(file.getContentType())
                        .targetId(targetId)
                        .targetType(targetType)
                        .build();

                savedEntities.add(uploadFile);

            } catch (IOException e) {
                throw new IllegalStateException("파일 저장 중 오류 발생", e);
            }
        }

        List<UploadFile> saved = uploadFileRepository.saveAll(savedEntities);

        return saved.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResponseDto> getFileList(Long targetId, TargetType targetType) {
        List<UploadFile> files = uploadFileRepository.findAllByTargetIdAndTargetType(targetId, targetType);
        return files.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FileResponseDto getFile(Long fileId) {
        UploadFile file = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다. id: " + fileId));
        return FileResponseDto.fromEntity(file);
    }


    @Override
    @Transactional
    public List<FileResponseDto> replaceFiles(Long targetId, TargetType targetType, List<Long> keepIds, List<MultipartFile> newFiles) {
        if (keepIds == null) {
            keepIds = List.of();
        }
        uploadFileRepository.deleteByTargetIdAndTargetTypeAndIdNotIn(targetId, targetType, keepIds);

        List<FileResponseDto> savedNewFiles = uploadMultiple(newFiles, targetId, targetType);

        List<UploadFile> finalFiles = uploadFileRepository.findAllByTargetIdAndTargetType(targetId, targetType);

        return finalFiles.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void deleteFile(Long fileId) {
        UploadFile file = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다. id: " + fileId));

        File physicalFile = new File(file.getFilePath() + file.getFileName());
        if (physicalFile.exists()) {
            physicalFile.delete();
        }

        uploadFileRepository.deleteById(fileId);
    }


}
