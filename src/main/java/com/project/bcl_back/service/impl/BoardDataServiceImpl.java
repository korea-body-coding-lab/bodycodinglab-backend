package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.BoardRequestDto;
import com.project.bcl_back.dto.board.response.BoardResponseDto;
import com.project.bcl_back.entity.Board;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.repository.BoardRepository;
import com.project.bcl_back.repository.UploadFileRepository;
import com.project.bcl_back.service.BoardDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.project.bcl_back.common.enums.TargetType;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardDataServiceImpl implements BoardDataService {
    private final BoardRepository boardRepo;
    private final UploadFileRepository fileRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    @Transactional
    public ResponseDto<BoardResponseDto> createPost(BoardRequestDto dto, MultipartFile file) throws IOException {
        // 게시글 저장
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        board = boardRepo.save(board);

        // 첨부파일 저장
        if (file != null && !file.isEmpty()) {
            saveFile(file, board.getId(), "Board");
        }

        BoardResponseDto data = toDto(board);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", data);
    }

    @Override
    public ResponseDto<List<BoardResponseDto>> getAllPosts() {
        List<BoardResponseDto> list = boardRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseMessage.SUCCESS, "", list);
    }

    @Override
    public ResponseDto<BoardResponseDto> getPostById(Long id) {
        Board board = boardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(board));
    }

    @Override
    @Transactional
    public ResponseDto<BoardResponseDto> updatePost(Long id, BoardRequestDto dto, MultipartFile file) throws IOException {
        Board board = boardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        boardRepo.save(board);

        List<UploadFile> existing = fileRepo.findByTargetTypeAndBoard_Id("BOARD", id);
        for (UploadFile uploadFile : existing) {
            new File(uploadDir + "/" + uploadFile.getFileName()).delete();
            fileRepo.delete(uploadFile);
        }

        if(file != null && !file.isEmpty()) {
            saveFile(file, id, "BOARD");
        }

        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(board));
    }

    @Override
    @Transactional
    public ResponseDto<?> deletePost(Long id) {
        List<UploadFile> files = fileRepo.findByTargetTypeAndBoard_Id("BOARD", id);
        for (UploadFile uploadFile : files) {
            new File(uploadDir + "/" + uploadFile.getFileName()).delete();
            fileRepo.delete(uploadFile);
        }
        boardRepo.deleteById(id);
        return ResponseDto.success(ResponseMessage.SUCCESS, null);
    }

    // Post -> DTO 변환
    private BoardResponseDto toDto(Board board){
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt().format(FORMAT))
                .build();
    }

    // 파일 저장 및 메타데이터 기록
    private void saveFile(MultipartFile file, Long targetId, String type)throws IOException{
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String uuid = UUID.randomUUID() + "_" + original;
        file.transferTo(new File(uploadDir + "/" + uuid));

        TargetType targetType = TargetType.valueOf(type.toUpperCase());

        UploadFile uploadFile = UploadFile.builder()
                .originalName(original)
                .fileName(uuid)
                .filePath("/files/" + uuid)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .targetType(targetType)
                .build();
        fileRepo.save(uploadFile);
    }
}
