package com.project.board_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note_text ", nullable = false)
    private String noteText;
    
    @Column(name = "note_writer",nullable = false)
    private Long noteWriter;
    
    @Column(name = "note_receiver", nullable = false)
    private Long noteReceiver;
    
    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "created_at")
    private LocalDateTime noteCreateTime;
}
