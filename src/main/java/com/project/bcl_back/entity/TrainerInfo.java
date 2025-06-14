package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trainer_infos")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "job_address")
    private String jobAddress;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "attachment_file_id")
    private UploadFile attachmentFile;

    @Column(name = "short_introduce")
    private String shortIntroduce;

    @Column(name = "long_introduce")
    private String longIntroduce;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private TrainerStatus trainerStatus;

    @Column(name = "education_name")
    private String educationName;

    @Column(name = "education_entrance")
    private Date educationEntrance;

    @Column(name = "education_graduate")
    private Date educationGraduate;

    // user 테이블에 넣어야 할 것
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private TrainerInfo trainerInfo;

    @OneToMany(mappedBy = "trainerInfo", cascade = CascadeType.ALL)
    private List<TrainerCareer> trainerCareers;

    @OneToMany(mappedBy = "trainerInfo", cascade = CascadeType.ALL)
    private List<TrainerLicense> trainerLicenses;
}
