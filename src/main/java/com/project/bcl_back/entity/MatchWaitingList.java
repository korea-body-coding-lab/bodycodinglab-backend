package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "match_waiting_list",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"member_Id", "trainer_id"})}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class MatchWaitingList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Column(name = "applied_date", nullable = false)
    private LocalDateTime appliedDate;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;
}
