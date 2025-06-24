package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.TargetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "upload_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
//    private Board board;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trainer_info_id")
//    private TrainerInfo trainerInfo;

    @Builder
    public UploadFile(String originalName, String fileName, String filePath, String fileType, Long fileSize, Long targetId, TargetType targetType){
        this.originalName = originalName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.targetId = targetId;
        this.targetType = targetType;
    }
//    public void assignToBoard(Board board){
//        this.board = board;
//        board.getImages().add(this);
//    }

    public void updateFile(String originalName, String fileName, String fileType, Long fileSize) {
        this.originalName = originalName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public String getFullUrl() {
        return "/files/" + this.fileName;
    }
}
