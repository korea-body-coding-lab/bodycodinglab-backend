package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long reviewId;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "cotent_image")
    private List<String> contentImages;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "review_score", nullable = false)
    private short reviewScore;

    @Column(name = "recommend_count", nullable = false)
    private int recommendCount;

}
