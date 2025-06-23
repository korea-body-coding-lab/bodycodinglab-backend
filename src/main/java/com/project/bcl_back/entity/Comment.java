package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "personal_community_board_comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "personal_community_board_post")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "commenter_id",nullable = false)
    private Long commenterId;

    @Column(name = "comment_content ", nullable = false)
    private String commentContent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected  void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
