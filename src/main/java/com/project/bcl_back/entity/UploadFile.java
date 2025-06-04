package com.project.bcl_back.entity;


import com.project.bcl_back.common.enums.TargetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private long fileSize;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;// 커몬 > 이넘 > 타겟타입에 파일저장소와 연결할 부분 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Board board;

    @Builder
    public UploadFile(String originalName, String fileName, String filePath, String fileType, long fileSize, TargetType targetType){
        this.originalName = originalName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.targetType = targetType;
    }
    public void assignToBoard(Board board){
        this.board = board;
        board.getImages().add(this);
    }
    // 파일 업로드 시스템이 필요한 부분마다 연결
}
