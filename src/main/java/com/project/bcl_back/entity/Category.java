package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.category.Categories;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private Categories categoryName;

}
