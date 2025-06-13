package com.project.bcl_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(
        name = "matches",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_Id", "trainer_id"}),
                @UniqueConstraint(columnNames = {"member_Id"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id",nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id" ,nullable = false)
    private User trainer;

    @Column(name = "matched_at", nullable = false)
    private LocalDateTime matchedAt;

    @Column(name = "is_maintained")
    private Boolean isMaintained = true;
}
