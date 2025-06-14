package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.onedayTicket.OneDayTicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "one_day_tickets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OneDayTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    @Column(name = "issued_at", nullable = false)
    private LocalDate issuedAt;

    @Column(name = "used_at")
    private LocalDate usedAt;

    @Column(name = "canceled_at")
    private LocalDate canceledAt;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private OneDayTicketStatus status;
}
