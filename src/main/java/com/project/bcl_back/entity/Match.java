package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(
        name = "matches",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_Id", "trainer_id"}),
                @UniqueConstraint(columnNames = {"member_Id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Column(name = "matched_at", nullable = false)
    private Date matchedAt;

    @Column(name = "is_maintained")
    private Boolean isMaintained = true;
}
