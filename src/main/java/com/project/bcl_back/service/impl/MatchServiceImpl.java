package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchResponseDto;
import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
import com.project.bcl_back.entity.Match;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.MatchRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.MatchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<TrainerMatchResponseDto> findMatchTrainer(Long userId) {
        TrainerMatchResponseDto response = null;

        User member = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        response = new TrainerMatchResponseDto(
                member.getMemberMatch().getTrainer().getTrainerInfo().getId(),
                member.getMemberMatch().getTrainer().getUsername(),
                member.getMemberMatch().getTrainer().getTrainerInfo().getTrainerLicenses().stream().map(
                        TrainerLicense::getLicenseName
                ).collect(Collectors.toList())
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<List<MemberMatchListResponseDto>> findMatchMemberList(Long userId) {
        List<MemberMatchListResponseDto> matchList = null;

        User trainer = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        matchList = trainer.getTrainerMatches().stream()
                .map(match ->{

                    LocalDate birthdate = match.getMember().getBirthdate();
                    int age = Year.now().getValue() - birthdate.getYear();

                    return new MemberMatchListResponseDto(
                            match.getMember().getName(),
                            age,
                            match.getMember().getGender()
                    );

                }).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, matchList);
    }


    @Override
    public ResponseDto<MemberMatchResponseDto> findMatchMember(Long matchId) {
        MemberMatchResponseDto response = null;
        Match match  = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchId));

        LocalDate birthdate = match.getMember().getBirthdate();
        int age = Year.now().getValue() - birthdate.getYear();

        if(match.getMember().getMember().getMemberForm() != null){
            response = new MemberMatchResponseDto(
                    match.getMember().getName(),
                    age,
                    match.getMember().getGender(),
                    match.getMember().getPhone(),
                    match.getMember().getMember().getMemberAddress(),
                    match.getMember().getMember().getMemberForm()
            );
        } else {
            response  = new MemberMatchResponseDto(
                    match.getMember().getName(),
                    age,
                    match.getMember().getGender(),
                    match.getMember().getPhone(),
                    match.getMember().getMember().getMemberAddress()
            );
        }
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> cancelMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchId));

        matchRepository.delete(match);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

}
