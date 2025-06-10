package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.trainerInfo.Status;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_file_id")
    private UploadFile attachmentFile;

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

    // user 테이블에 넣어야 할 것
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private TrainerInfo trainerInfo;

    @OneToMany(mappedBy = "trainer_infos", cascade = CascadeType.ALL)
    private List<TrainerCareer> trainerCareer = new ArrayList<>();

    @OneToMany(mappedBy = "trainer_infos", cascade = CascadeType.ALL)
    private List<TrainerLicense> trainerLicense = new ArrayList<>();

    public void addCareer(TrainerCareer career) {
        this.trainerCareer.add(career);
        career.setTrainerInfo(this);
    }

    public void removeCareer(TrainerCareer career) {
        this.trainerCareer.add(career);
        career.setTrainerInfo(null);
    }

    public void addLicense(TrainerLicense license) {
        this.trainerLicense.add(license);
        license.setTrainerInfo(this);
    }

    public void removeLicense(TrainerLicense license) {
        this.trainerLicense.add(license);
        license.setTrainerInfo(null);
    }
}
