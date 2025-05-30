package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.trainerInfo.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trainer_infos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "job_address")
    private String jobAddress;

    @Column(name = "short_introduce")
    private String shortIntroduce;

    @Column(name = "long_introduce")
    private String LongIntroduce;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "education_name")
    private String educationName;

    @Column(name = "education_entrance")
    private Date educationEntrance;

    @Column(name = "education_graduate")
    private Date educationGraduate;

    @OneToOne
    @JoinColumn(name = "id")
    private User user;

    // user 테이블에 넣어야 할 것
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private TrainerInfo trainerInfo;

    @OneToMany(mappedBy = "trainer_infos", cascade = CascadeType.ALL)
    private List<TrainerCareer> trainerCareer = new ArrayList<>();

    @OneToMany(mappedBy = "trainer_infos", cascade = CascadeType.ALL)
    private List<TrainerLicense> trainerLicense = new ArrayList<>();
}
