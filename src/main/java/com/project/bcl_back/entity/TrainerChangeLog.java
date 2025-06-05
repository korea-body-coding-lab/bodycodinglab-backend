package com.project.bcl_back.entity;

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

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "prev_status")
    private String prevStatus;

    @Column(name = "new_status")
    private String newStatus;

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "change_reason")
    private String changeReason;

    @Builder
    public TrainerChangeLog(Long trainerId, Long userId, String prevStatus, String newStatus, String changedBy, String changeReason) {
        this.trainerId = trainerId;
        this.userId = userId;
        this.prevStatus = prevStatus;
        this.newStatus = newStatus;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
    }
}
