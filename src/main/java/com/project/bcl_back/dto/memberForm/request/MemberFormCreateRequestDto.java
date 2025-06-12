package com.project.bcl_back.dto.memberForm.request;

import com.project.bcl_back.common.enums.memberFrom.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberFormCreateRequestDto {
    private boolean isSubmit;
    @NotBlank
    private Byte age;
    @NotBlank
    private BodyForm bodyForm;
    @NotBlank
    private Goal goal;
    @NotBlank
    private Short bmi;
    @NotBlank
    private Improved_part improvedPart;
    @NotBlank
    private PreferredDiet preferredDiet;
    @NotBlank
    private SugarIntake sugarIntake;
    @NotBlank
    private WaterIntake waterIntake;
    @NotBlank
    private Short height;
    @NotBlank
    private Short weight;
    @NotBlank
    private Short weightGoal;
    @NotBlank
    private Short physicalLevel;
    @NotBlank
    private ExercisingProblem exercisingProblem;
    @NotBlank
    private PushupLevel pushupLevel;
    @NotBlank
    private PullupLevel pullupLevel;
    @NotBlank
    private ExerciseFrequency exerciseFrequency;
    @NotBlank
    private InvestableTime investableTime;
}
