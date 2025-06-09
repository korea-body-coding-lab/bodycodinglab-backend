package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.member.MemberStatus;
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
    private MemberStatus status = MemberStatus.NOT_PAYMENT;

    @Column(name = "is_approved", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isApproved;

    @OneToOne(mappedBy = "Member", cascade = CascadeType.ALL)
    private MemberForm memberFrom;

    @Builder
    public Member(User user, String memberAddress, MemberStatus status) {
        this.user = user;
        this.memberAddress = memberAddress;
        this.status = status;
    }

}
