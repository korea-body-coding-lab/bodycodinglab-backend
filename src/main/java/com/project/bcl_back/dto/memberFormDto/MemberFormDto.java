package com.project.bcl_back.dto.memberFormDto;

import com.project.bcl_back.common.enums.memberFrom.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberFormDto {
    private BodyForm bodyForm;
    private Goal goal;
    private Bmi bmi;
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
