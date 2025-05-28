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
    private String note_text;
    
    @Column(nullable = false)
    private Long note_writer;
    
    @Column(nullable = false)
    private Long note_receiver;
    
    @Column
    private boolean is_read;

    @Column
    private LocalDateTime note_write_datetime;
}
