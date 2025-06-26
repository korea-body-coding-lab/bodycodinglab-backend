package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.FileResponseDto;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.board.request.BoardRequestDto;
import com.project.bcl_back.dto.board.response.BoardResponseDto;
import com.project.bcl_back.entity.Board;
import com.project.bcl_back.entity.Category;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.repository.BoardRepository;
import com.project.bcl_back.repository.CategoryRepository;
import com.project.bcl_back.repository.UploadFileRepository;
import com.project.bcl_back.service.BoardDataService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.project.bcl_back.common.enums.TargetType;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardDataServiceImpl implements BoardDataService {
    private final BoardRepository boardRepo;
    private final UploadFileRepository fileRepo;
    private final CategoryRepository categoryRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    @Transactional
    public ResponseDto<BoardResponseDto> createPost(BoardRequestDto dto, Long matchId, List<MultipartFile> files) throws IOException {
        if (!dto.getMatchId().equals(matchId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        Category category = categoryRepository.findById(dto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
        // 게시글 저장
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(category)
                .writerId(dto.getWriterId())
                .matchId(dto.getMatchId())
                .createdAt(LocalDateTime.now())
                .viewCount(dto.getViewCount())
                .build();
        board = boardRepo.save(board);

        board = boardRepo.findById(board.getId())
                .orElseThrow(() -> new RuntimeException("게시글 조회 실패"));
        // 첨부파일 저장
        if (files != null && !files.isEmpty()) {
            saveFiles(files, board.getId(), "BOARD");;
        }

        BoardResponseDto data = toDto(board);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", data);
    }

    @Override
    public ResponseDto<Page<BoardResponseDto>> getPostByCategoryAndMatchId(int categoryId, Long matchId, Pageable pageable) {

        Page<Board> boardPage = boardRepo.findByCategoryIdAndMatchId(categoryId, matchId, pageable);


        Page<BoardResponseDto> dtoPage = boardPage.map(this::toDto);


        return ResponseDto.success(ResponseMessage.SUCCESS, "", dtoPage);
    }

    @Override
    public ResponseDto<BoardResponseDto> getPostById(Long id, Long matchId) {
        Board board = boardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        if (!board.getMatchId().equals(matchId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(board));
    }

    @Override
    @Transactional
    public ResponseDto<BoardResponseDto> updatePost(Long id, Long matchId, BoardRequestDto dto, List<MultipartFile> files) throws IOException {

        Board board = boardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        if (!board.getMatchId().equals(matchId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setUpdatedAt(LocalDateTime.now());
        boardRepo.save(board);

        List<UploadFile> existing = fileRepo.findByTargetTypeAndTargetId(TargetType.BOARD, id);

        for (UploadFile uploadFile : existing) {
            new File(uploadDir + "/" + uploadFile.getFileName()).delete();
            fileRepo.delete(uploadFile);
        }

        //Error
        if(files != null && !files.isEmpty()) {
            saveFiles(files, board.getId(), "BOARD");
        }

        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(board));
    }

    @Override
    @Transactional
    public ResponseDto<?> deletePost(Long id, Long matchId) {
        Board board = boardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (!board.getMatchId().equals(matchId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        List<UploadFile> files = fileRepo.findByTargetTypeAndTargetId(TargetType.BOARD, id);
        for (UploadFile uploadFile : files) {
            new File(uploadDir + "/" + uploadFile.getFileName()).delete();
            fileRepo.delete(uploadFile);
        }

        boardRepo.deleteById(id);
        return ResponseDto.success(ResponseMessage.SUCCESS, null);
    }

    // Post -> DTO 변환
    private BoardResponseDto toDto(Board board){
        String writerName = null;
        String profileImageUrl = null;

        if (board.getWriter() != null) {
            writerName = board.getWriter().getName();
            UploadFile profileImage = board.getWriter().getProfileImage();
            if(profileImage != null){
                profileImageUrl = profileImage.getFullUrl();
            }
        }
        List<UploadFile> uploadFiles = fileRepo.findByTargetTypeAndTargetId(TargetType.BOARD, board.getId());
        List<FileResponseDto> imageDtos = uploadFiles.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());
        return BoardResponseDto.builder()
                .id(board.getId())
                .writerId(board.getWriterId())
                .writerName(writerName)
                .profileImageUrl(profileImageUrl)
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt().format(FORMAT))
                .images(imageDtos)
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
                .targetId(targetId)
                .build();
        fileRepo.save(uploadFile);
    }
    private void saveFiles(List<MultipartFile> files, Long targetId, String type) throws IOException {
        if (files == null || files.isEmpty()) return;
        for (MultipartFile file : files) {
            saveFile(file, targetId, type);
        }
    }
    @Override
    public boolean isPostWriter(Long userId, Long postId) {
        Board post = boardRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        return post.getWriter().getId().equals(userId);
    }
}
