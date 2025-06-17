package com.project.bcl_back.dto.memberForm.request;

import com.project.bcl_back.common.enums.memberFrom.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberFormCreateRequestDto {
    @NotNull(message = "체형은 필수입니다.")
    private BodyForm bodyForm;
    @NotNull(message = "목표는 필수입니다.")
    private Goal goal;
    @NotNull(message = "BMI는 필수입니다.")
    private Bmi bmi;
    @NotNull(message = "향상시키고픈 부위는 필수입니다.")
    private Improved_part improvedPart;
    @NotNull(message = "따르는 식단은 필수입니다.")
    private PreferredDiet preferredDiet;
    @NotNull(message = "당 섭취 빈도는 필수입니다.")
    private SugarIntake sugarIntake;
    @NotNull(message = "수분 섭취 빈도는 필수입니다.")
    private WaterIntake waterIntake;
    @NotNull(message = "신장값은 필수입니다..")
    private Short height;
    @NotNull(message = "현제 체중값은 필수 입니다..")
    private Short weight;
    @NotNull(message = "목표 체중값은 필수입니다.")
    private Short weightGoal;
    @NotNull(message = "신체능력 점수값는 필수 입니다.")
    private Short physicalLevel;
    @NotNull(message = "운동시 겪었던 문제는 필수 입니다..")
    private ExercisingProblem exercisingProblem;
    @NotNull(message = "팔굽혀펴기 갯수는 필수입니다.")
    private PushupLevel pushupLevel;
    @NotNull(message = "턱걸이 갯수는 필수 입니다.")
    private PullupLevel pullupLevel;
    @NotNull(message = "운동 빈도는 필수입니다.")
    private ExerciseFrequency exerciseFrequency;
    @NotNull(message = "운동 투자 가능 시간 여부는 필수입니다.")
    private InvestableTime investableTime;
}
