package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.trainerLicense.LicenseType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "trainer_licenses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "trainer_id")
    private Long trainerId;

    @Column(nullable = false, name = "license_type")
    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    @Column(nullable = false, name = "license_name")
    private String licenseName;

    @ManyToOne
    @JoinColumn(name = "id")
    private TrainerInfo trainerInfo;
}
