package com.project.bcl_back.entity;

import com.project.bcl_back.common.enums.memberFrom.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_forms")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long formId;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "is_submit", nullable = false)
    private boolean isSubmit = false;

    @Column(name = "age", nullable = false)
    private Byte age;

    @Enumerated(EnumType.STRING)
    @Column(name = " bodyform",nullable = false, length = 10)
    private BodyForm bodyForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal",nullable = false, length = 20)
    private Goal goal;

    @Column(name = "bmi", nullable = false)
    private short bmi;

    @Enumerated(EnumType.STRING)
    @Column(name = "improved_part", nullable = false, length = 20)
    private Improved_part improvedPart;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_diet", nullable = false, length = 20)
    private PreferredDiet preferredDiet;

    @Enumerated(EnumType.STRING)
    @Column(name = "sugar_intake", nullable = false, length = 20)
    private SugarIntake sugarIntake;

    @Enumerated(EnumType.STRING)
    @Column(name = "water_intake", nullable = false, length = 20)
    private WaterIntake waterIntake;

    @Column(name = "height", nullable = false)
    private short height;

    @Column(name = "weight", nullable = false)
    private short weight;

    @Column(name = "weight_goal", nullable = false)
    private short weightGoal;

    @Column(name = "physical_level", nullable = false)
    private short physicalLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercising_problem", nullable = false, length = 20)
    private ExercisingProblem exercisingProblem;

    @Enumerated(EnumType.STRING)
    @Column(name = "pushup_level", nullable = false, length = 20)
    private PushupLevel pushupLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "pullup_level", nullable = false, length = 20)
    private PullupLevel pullupLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_frequency", nullable = false, length = 20)
    private ExerciseFrequency exerciseFrequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "investable_time", nullable = false, length = 20)
    private InvestableTime investableTime;
}
