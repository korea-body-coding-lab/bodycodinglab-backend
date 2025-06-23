package com.project.bcl_back.controller;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(ApiMappingPattern.FILE_API)
@RequiredArgsConstructor
public class UploadFileController {
    private final UploadFileService uploadFileService;

    private static final String PROFILE_URL = "/profile";
    private static final String TRAINER_ATTACHMENT_URL = "/trainer-attachment";

    @GetMapping(PROFILE_URL + "/{targetId}/{targetType}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable Long targetId, @PathVariable TargetType targetType) {
        UploadFile uploadFile = uploadFileService.findByTargetIdAndTargetType(targetId, targetType);

        Path path = Paths.get(uploadFile.getFilePath(), uploadFile.getFileName());
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);
    }

    @GetMapping(TRAINER_ATTACHMENT_URL + "/{targetId}/{targetType}")
    public ResponseEntity<Resource> getTrainerAttachmentFile(@PathVariable Long targetId, @PathVariable TargetType targetType) {
        UploadFile uploadFile = uploadFileService.findByTargetIdAndTargetType(targetId, targetType);

        Path path = Paths.get(uploadFile.getFilePath(), uploadFile.getFileName());
        Resource resource = new FileSystemResource(path);

        String encodedFilename = URLEncoder.encode(uploadFile.getOriginalName(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                .body(resource);
    }

    @PutMapping("/{targetId}/{targetType}")
    public ResponseEntity<UploadFile> updateFile(
            @PathVariable Long targetId,
            @PathVariable TargetType targetType,
            @RequestPart("file") MultipartFile newFile
    ) throws IOException {
        UploadFile updatedFile = uploadFileService.updateFile(targetId, targetType, newFile);
        return ResponseEntity.ok(updatedFile);
    }

    @DeleteMapping (value = "/{targetId}/{targetType}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long targetId,
            @PathVariable TargetType targetType
    ) throws IOException {
        uploadFileService.deleteFile(targetId, targetType);
        return ResponseEntity.noContent().build();
    }

}
