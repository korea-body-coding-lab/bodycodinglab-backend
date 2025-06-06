package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import com.project.bcl_back.entity.MemberForm;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.MemberFormRepository;
import com.project.bcl_back.repository.MemberRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.MemberFormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFormServiceImpl implements MemberFormService {

    private final UserRepository userRepository;
    private final MemberFormRepository memberFormRepository;

    @Override
    public ResponseDto<Void> createMemberForm(MemberFormCreateRequestDto dto) {
        User user = userRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + dto.getMemberId()));

        MemberForm memberForm = new MemberForm(
              null,
              user,
              dto.getAge(),
              dto.getBodyForm(),
              dto.getGoal(),
              dto.getBmi(),
              dto.getImprovedPart(),
              dto.getPreferredDiet(),
              dto.getSugarIntake(),
              dto.getWaterIntake(),
              dto.getHegiht(),
              dto.getWeight(),
              dto.getWeightGoal(),
              dto.getPhysicalLevel(),
              dto.getExercisingProblem(),
              dto.getPushupLevel(),
              dto.getPullupLevel(),
              dto.getExerciseFrequency(),
              dto.getInvestableTime()
        );

        memberFormRepository.save(memberForm);
        return null;
    }

    @Override
    public ResponseDto<MemberFormResponseDto> findByFromIdMemberForm(Long formId) {
        MemberFormResponseDto response = null;

        MemberForm memberForm = memberFormRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + formId));

        response = new MemberFormResponseDto(
                memberForm.getMember().getId(),
                memberForm.getMember().getUsername(),
                memberForm.getAge(),
                memberForm.getBodyForm(),
                memberForm.getGoal(),
                memberForm.getBmi(),
                memberForm.getImprovedPart(),
                memberForm.getPreferredDiet(),
                memberForm.getSugarIntake(),
                memberForm.getWaterIntake(),
                memberForm.getHeight(),
                memberForm.getWeight(),
                memberForm.getWeightGoal(),
                memberForm.getPhysicalLevel(),
                memberForm.getExercisingProblem(),
                memberForm.getPushupLevel(),
                memberForm.getPullupLevel(),
                memberForm.getExerciseFrequency(),
                memberForm.getInvestableTime()
        );

        return null;
        //return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
