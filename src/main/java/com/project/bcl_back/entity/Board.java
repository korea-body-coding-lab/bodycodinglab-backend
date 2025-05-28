package com.project.board_back.entity;

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

    @Column(nullable = false)
    private String writer_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String image_url;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "personal_community_board_post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

//    @PrePersist
//    public void prePersist(){
//        this.created_at = LocalDateTime.now();
//        this.updated_at = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdated(){
//        this.updated_at = LocalDateTime.now();
//    }
//
//    public void addComment(Comment comment){
//        this.comments.add(comment);
//        comment.setPost(this);
//    }
//
//    public void removeComment(Comment comment){
//        this.comments.remove(comment);
//        comment.setPost(null);
//    }

//    public void addPost(Post post){
//        this.posts.add(post);
//        post.setCommunityBoard(this);
//    }
//
//    public void removePost(Post post){
//        this.posts.remove(post);
//        post.setCommunityBoard(null);
//    }
}
