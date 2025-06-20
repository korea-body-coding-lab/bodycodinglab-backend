package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ApiMappingPattern;
import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.TargetType;
import com.project.bcl_back.common.enums.matchWaitingList.ApprovedStatus;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import com.project.bcl_back.entity.MatchWaitingList;
import com.project.bcl_back.entity.UploadFile;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.MatchWaitingListRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.MatchWaitingListService;
import com.project.bcl_back.service.UploadFileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.bcl_back.common.util.DateUtils.nowFormated;
import static com.project.bcl_back.common.util.DateUtils.parse;

@Service
@RequiredArgsConstructor
public class MatchWaitingListServiceImpl implements MatchWaitingListService {
    private final MatchWaitingListRepository matchWaitingListRepository;
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;


    @Override
    @Transactional
    public ResponseDto<Long> createMatchWaitingList(Long trainerId, Long userId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + trainerId));

        User member = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException((ResponseMessage.USER_NOT_FOUND + trainerId)));

        MatchWaitingList matchWaitingList = new MatchWaitingList(
                null,
                member,
                trainer,
                parse(nowFormated()),
                ApprovedStatus.NOT_APPROVED
        );

        member.setMatchWaitingListAsMember(matchWaitingList);
        trainer.addMatchWaitingListAsTrainers(matchWaitingList);

        matchWaitingListRepository.save(matchWaitingList);



        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, matchWaitingList.getId());
    }

    @Override
    public ResponseDto<List<MemberMatchWaitingListResponseDto>> findTrainerWaitingList(Long trainerId) {
        List<MemberMatchWaitingListResponseDto> response = null;

        List<MatchWaitingList> lists = matchWaitingListRepository.findByTrainer_Id(trainerId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + trainerId));

        response = lists.stream()
                .map(list ->{

                            LocalDate birthdate = list.getMember().getBirthdate();
                            int age = Period.between(birthdate, LocalDate.now()).getYears();

                            return new MemberMatchWaitingListResponseDto(
                                    list.getId(),
                                    list.getMember().getId(),
                                    list.getMember().getName(),
                                    age,
                                    list.getMember().getGender(),
                                    list.getAppliedAt(),
                                    list.getApprovedStatus()
                            );
                }
                ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<TrainerMatchWaitingListResponseDto> findMemberMatchWaitingList(Long memberId) {
        TrainerMatchWaitingListResponseDto response = null;

        MatchWaitingList list = matchWaitingListRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + memberId));

        String profileImageUrl = null;
        UploadFile profileImage = uploadFileService.findByTargetIdAndTargetType(list.getTrainer().getId(), TargetType.PROFILE);
        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/profile/" + list.getTrainer().getId() + "/" + TargetType.PROFILE;
        }
        response = new TrainerMatchWaitingListResponseDto(
                list.getId(),
                list.getTrainer().getId(),
                profileImageUrl,
                list.getTrainer().getName(),
                list.getTrainer().getTrainerInfo().getJobAddress(),
                list.getAppliedAt(),
                list.getApprovedStatus()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public  ResponseDto<Void> matchApprove(Long matchWaitingListId, MatchWaitingListRequestDto dto) {
        MatchWaitingList matchWaitingList = matchWaitingListRepository.findById(matchWaitingListId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchWaitingListId));

        matchWaitingList.setApprovedStatus(dto.getApprovedStatus());

        matchWaitingListRepository.save(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> matchReject(Long matchWaitingListId, MatchWaitingListRequestDto dto) {
        MatchWaitingList matchWaitingList  = matchWaitingListRepository.findById(matchWaitingListId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchWaitingListId));

        matchWaitingList.setApprovedStatus(dto.getApprovedStatus());



        Long memberId = matchWaitingList.getMember().getId();
        Long trainerId = matchWaitingList.getTrainer().getId();

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + memberId));

        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + trainerId));

        member.setMatchWaitingListAsMember(null);
        trainer.removeMatchWaitingListAsTrainers(matchWaitingList);

        userRepository.save(member);
        userRepository.save(trainer);

        matchWaitingListRepository.delete(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> matchCancel(Long memberId, MatchWaitingListRequestDto dto) {

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException((ResponseMessage.RESOURCE_NOT_FOUND + memberId)));


        MatchWaitingList list = matchWaitingListRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + memberId));


        member.setMatchWaitingListAsMember(null);
        list.setApprovedStatus(dto.getApprovedStatus());

        matchWaitingListRepository.delete(list);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

}
