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

    @Column(nullable = false)
    private String noteText;
    
    @Column(nullable = false)
    private Long noteWriter;
    
    @Column(nullable = false)
    private Long noteReceiver;
    
    @Column
    private boolean isRead;

    @Column
    private LocalDateTime noteWriteDatetime;
}
