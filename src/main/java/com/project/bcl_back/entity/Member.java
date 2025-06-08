package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.member.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long memberId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "member_address", nullable = false)
    private String memberAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NOT_PAYMENT;

    @Column(name = "is_approved", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isApproved;

    @Builder
    public Member(User user, String memberAddress, Status status) {
        this.user = user;
        this.memberAddress = memberAddress;
        this.status = status;
    }

}
