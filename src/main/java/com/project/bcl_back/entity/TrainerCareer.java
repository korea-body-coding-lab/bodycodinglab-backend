package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "trainer_careers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerCareer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "trainer_id")
    private Long trainerId;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, name = "company_join")
    private Date companyJoin;

    @Column(nullable = false, name = "company_quit")
    private Date companyQuit;

    @ManyToOne
    @JoinColumn(name = "id")
    private TrainerInfo trainerInfo;

}
