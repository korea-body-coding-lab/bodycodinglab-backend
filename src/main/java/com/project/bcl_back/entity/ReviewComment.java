package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_comment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long commentId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "id", unique = true)
    private Review review;
}
