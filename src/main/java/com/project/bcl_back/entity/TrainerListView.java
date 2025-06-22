package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainer_list_view")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
@Getter
public class TrainerListView {
    @Id
    @Column(name = "trainer_id")
    private Long trainerId;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "job_address")
    private String jobAddress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TrainerStatus status;

}
