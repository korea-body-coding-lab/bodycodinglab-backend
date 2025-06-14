package com.project.bcl_back.dto.memberForm.response;

import com.project.bcl_back.common.enums.memberFrom.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberFormResponseDto {
    private Long MemberId;
    private String memberName;
    private BodyForm bodyForm;
    private Goal goal;
    private Short bmi;
    private Improved_part improvedPart;
    private PreferredDiet preferredDiet;
    private SugarIntake sugarIntake;
    private WaterIntake waterIntake;
    private Short height;
    private Short weight;
    private Short weightGoal;
    private Short physicalLevel;
    private ExercisingProblem exercisingProblem;
    private PushupLevel pushupLevel;
    private PullupLevel pullupLevel;
    private ExerciseFrequency exerciseFrequency;
    private InvestableTime investableTime;
}
