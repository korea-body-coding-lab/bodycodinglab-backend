package com.project.board_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personal_community_board_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "personal_community_board")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category_name;

}
