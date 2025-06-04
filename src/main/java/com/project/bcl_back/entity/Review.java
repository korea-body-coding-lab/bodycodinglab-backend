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

    @ManyToOne
    @JoinColumn (name = "id", nullable = false)
    private Match match;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;


    @Column(name = "created_at", insertable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "review_score", nullable = false)
    private Short reviewScore;

    @Column(name = "recommend_count", nullable = false)
    private Integer recommendCount;

    @OneToOne(mappedBy = "reviews", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ReviewComment reviewComment;

}
