package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.couponEnums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long couponId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Lob
    @Column(name = "coupon_image", columnDefinition = "LONGBLOB")
    private byte[] couponImage; // 질문 대기

    @Column(name = "expiration_period", nullable = false)
    private LocalDate expirationPeriod;

    @Column(name = "used_date", insertable = false, updatable = false)
    private LocalDateTime usedDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
