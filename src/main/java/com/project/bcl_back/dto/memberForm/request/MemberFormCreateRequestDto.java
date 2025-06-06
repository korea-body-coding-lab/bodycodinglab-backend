package com.project.bcl_back.dto.memberForm.request;

import com.project.bcl_back.common.enums.memberFrom.*;
import lombok.Getter;

@Getter
public class MemberFormCreateRequestDto {
    private Long MemberId;
    private Byte age;
    private BodyForm bodyForm;
    private Goal goal;
    private Short bmi;
    private Improved_part improvedPart;
    private PreferredDiet preferredDiet;
    private SugarIntake sugarIntake;
    private WaterIntake waterIntake;
    private Short hegiht;
    private Short weight;
    private Short weightGoal;
    private Short physicalLevel;
    private ExercisingProblem exercisingProblem;
    private PushupLevel pushupLevel;
    private PullupLevel pullupLevel;
    private ExerciseFrequency exerciseFrequency;
    private InvestableTime investableTime;
}
