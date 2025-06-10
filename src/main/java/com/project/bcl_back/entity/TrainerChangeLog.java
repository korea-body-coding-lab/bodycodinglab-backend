package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainer_change_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrainerChangeLog extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "prev_status")
    private TrainerStatus prevTrainerStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private TrainerStatus newTrainerStatus;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "change_reason")
    private String changeReason;

    @Builder
    public TrainerChangeLog(Long trainerId, String username, TrainerStatus prevTrainerStatus, TrainerStatus newTrainerStatus, Long changedBy, String changeReason) {
        this.trainerId = trainerId;
        this.username = username;
        this.prevTrainerStatus = prevTrainerStatus;
        this.newTrainerStatus = newTrainerStatus;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
    }
}
