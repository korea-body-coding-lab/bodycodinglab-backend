package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer_infos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
