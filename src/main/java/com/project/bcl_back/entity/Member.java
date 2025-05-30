package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.member.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_address", nullable = false)
    private String memberAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_approved", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isApproved;
}
