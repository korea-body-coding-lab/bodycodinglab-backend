package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.matchWaitingList.ApprovedStatus;
import com.project.bcl_back.common.util.DateUtils;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.matchWatingList.request.MatchWaitingListRequestDto;
import com.project.bcl_back.dto.matchWatingList.response.MemberMatchWaitingListResponseDto;
import com.project.bcl_back.dto.matchWatingList.response.TrainerMatchWaitingListResponseDto;
import com.project.bcl_back.entity.MatchWaitingList;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.MatchWaitingListRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.MatchWaitingListService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public ResponseDto<Void> createMatchWaitingList(Long trainerId, Long userId) {
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

        matchWaitingListRepository.save(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<List<MemberMatchWaitingListResponseDto>> findMemberWaitingList(Long trainerId) {
        List<MemberMatchWaitingListResponseDto> response = null;

        List<MatchWaitingList> lists = matchWaitingListRepository.findByTrainer_Id(trainerId);

        response = lists.stream()
                .map(list ->{

                            LocalDate birthdate = list.getMember().getBirthdate();
                            int age = Period.between(birthdate, LocalDate.now()).getYears();

                            return new MemberMatchWaitingListResponseDto(
                                    list.getMember().getId(),
                                    list.getMember().getName(),
                                    age,
                                    list.getMember().getGender(),
                                    parse(nowFormated()));
                        }
                ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<TrainerMatchWaitingListResponseDto> findTrainerMatchWaitingList(Long memberId) {
        TrainerMatchWaitingListResponseDto response = null;

        MatchWaitingList list = matchWaitingListRepository.findByMember_Id(memberId);

        response = new TrainerMatchWaitingListResponseDto(
                list.getTrainer().getId(),
                list.getTrainer().getName(),
                list.getTrainer().getTrainerInfo().getJobAddress(),
                list.getAppliedAt()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public  ResponseDto<Void> matchApprove(Long matchWaitingListId, MatchWaitingListRequestDto dto) {
        MatchWaitingList matchWaitingList = matchWaitingListRepository.findById(matchWaitingListId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchWaitingListId));

        matchWaitingList.setApprovedStatus(dto.getApprovedStatus());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<Void> matchReject(Long matchWaitingListId, MatchWaitingListRequestDto dto) {
        MatchWaitingList matchWaitingList  = matchWaitingListRepository.findById(matchWaitingListId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchWaitingListId));

        matchWaitingList.setApprovedStatus(dto.getApprovedStatus());

        matchWaitingListRepository.delete(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<Void> matchCancel(Long memberId, MatchWaitingListRequestDto dto) {

        MatchWaitingList list = matchWaitingListRepository.findByMember_Id(memberId);

        list.setApprovedStatus(dto.getApprovedStatus());

        matchWaitingListRepository.delete(list);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


}
