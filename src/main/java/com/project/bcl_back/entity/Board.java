package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal_community_board")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "personal_community_board_categories")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToOne(mappedBy = "matches") -- 매칭 테이블과 1대1 대응
    @JoinColumn(name = "match_id")
    private Long matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "writer_id",nullable = false)
    private Long writerId;

    @Column(name = "post_title",nullable = false)
    private String title;

    @Column(name = "post_content",nullable = false)
    private String content;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "personal_community_board_post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "personal_community_board_post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> images = new ArrayList<>();

    public void addImage(UploadFile file){
        images.add(file);
        file.assignToBoard(this);
    }
    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setBoard(this);
    }
    public void removeComment(Comment comment){
        this.comments.remove(comment);
        comment.setBoard(null);
    }
}
