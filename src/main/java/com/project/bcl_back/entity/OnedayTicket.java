package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.onedayTicket.OnedayTicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "oneday_tickets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OnedayTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "member_id")
    private Long memberId;

    @Column(nullable = false, name = "trainer_id")
    @JoinTable(name = "trainer_infos") @JoinColumn(name = "id")
    private Long trainerId;

    @Column(nullable = false, name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(nullable = false, name = "used_at")
    private LocalDateTime usedAt;

    @Column(nullable = false, name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private OnedayTicketStatus onedayTicketStatus;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

    // member 테이블에 넣어야 할 것
//    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL)
//    private List<OnedayTicket> tickets = new ArrayList<>();
}
