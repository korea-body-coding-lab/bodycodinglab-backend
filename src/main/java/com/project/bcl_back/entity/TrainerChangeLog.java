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
    private TrainerStatus prevStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private TrainerStatus newStatus;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "change_reason")
    private String changeReason;

    @Builder
    public TrainerChangeLog(Long id, TrainerInfo trainer, TrainerStatus prevStatus, String changeReason) {
        this.trainerId = trainer.getId();
        this.username = trainer.getUser().getUsername();
        this.prevStatus = prevStatus;
        this.newStatus = trainer.getTrainerStatus();
        this.changedBy = id;
        this.changeReason = changeReason;
    }
}
