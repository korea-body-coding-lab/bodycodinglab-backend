package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.memberForm.request.MemberFormCreateRequestDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import com.project.bcl_back.entity.Member;
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

    private final MemberRepository memberRepository;
    private final MemberFormRepository memberFormRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseDto<Long> createMemberForm(Long memberId, MemberFormCreateRequestDto dto) {
        User user = userRepository.findById(memberId).
                orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + memberId));

        Member member = memberRepository.findById(user.getMember().getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + memberId));

        MemberForm memberForm = new MemberForm(
              null,
              member,
              true,
              dto.getBodyForm(),
              dto.getGoal(),
              dto.getBmi(),
              dto.getImprovedPart(),
              dto.getPreferredDiet(),
              dto.getSugarIntake(),
              dto.getWaterIntake(),
              dto.getHeight(),
              dto.getWeight(),
              dto.getWeightGoal(),
              dto.getPhysicalLevel(),
              dto.getExercisingProblem(),
              dto.getPushupLevel(),
              dto.getPullupLevel(),
              dto.getExerciseFrequency(),
              dto.getInvestableTime()
        );

        member.setMemberForm(memberForm);
        memberFormRepository.save(memberForm);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, memberForm.getFormId());
    }

    @Override
    public ResponseDto<MemberFormResponseDto> findByFromIdMemberForm(Long memberId) {
        MemberFormResponseDto response = null;

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + memberId));

        MemberForm memberForm = memberFormRepository.findById(user.getMember().getMemberForm().getFormId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + memberId));

        response = new MemberFormResponseDto(
                memberForm.getMember().getMemberId(),
                memberForm.getMember().getUser().getName(),
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


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
