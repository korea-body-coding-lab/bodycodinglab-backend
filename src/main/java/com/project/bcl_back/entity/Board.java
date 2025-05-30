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

    @OneToMany(mappedBy = "personal_community_board_categories", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Category> category;

    @Column(name = "writer_id",nullable = false)
    private Long writerId;

    @Column(name = "post_title",nullable = false)
    private String title;

    @Column(name = "post_content",nullable = false)
    private String content;

    @Lob
    @Column(name = "post_image", columnDefinition = "LONGBLOB")
    private byte[] postImage;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "personal_community_board_post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


}
