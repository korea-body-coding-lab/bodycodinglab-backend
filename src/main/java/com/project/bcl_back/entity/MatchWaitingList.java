package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.matchWaitingList.ApprovedStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "match_waiting_list",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_Id", "trainer_id"}),
                @UniqueConstraint(columnNames = {"member_Id"})}
)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class MatchWaitingList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id" ,nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
    private User trainer;

    @Column(name = "applied_date", nullable = false)
    private LocalDateTime appliedDate;

    @Column(name = "approved_status", nullable = false)
    private ApprovedStatus approvedStatus;
}
