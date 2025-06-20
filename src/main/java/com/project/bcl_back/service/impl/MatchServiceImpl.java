package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
import com.project.bcl_back.dto.match.response.MemberMatchResponseDto;
import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
import com.project.bcl_back.dto.memberForm.response.MemberFormResponseDto;
import com.project.bcl_back.dto.memberFormDto.MemberFormDto;
import com.project.bcl_back.entity.Match;
import com.project.bcl_back.entity.Subscription;
import com.project.bcl_back.entity.TrainerLicense;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.MatchRepository;
import com.project.bcl_back.repository.SubscriptionRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.MatchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public ResponseDto<TrainerMatchResponseDto> findMemberMatch(Long userId) {
        TrainerMatchResponseDto response = null;

        User member = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        response = new TrainerMatchResponseDto(
                member.getMemberMatch().getId(),
                member.getMemberMatch().getTrainer().getTrainerInfo().getId(),
                member.getMemberMatch().getTrainer().getName(),
                member.getMemberMatch().getMatchedAt(),
                member.getMemberMatch().getTrainer().getTrainerInfo().getJobAddress(),
                member.getMemberMatch().getTrainer().getTrainerInfo().getTrainerLicenses().stream().map(
                        TrainerLicense::getLicenseName
                ).collect(Collectors.toList())
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<List<MemberMatchListResponseDto>> findMatchTrainerList(Long userId) {
        List<MemberMatchListResponseDto> matchList = null;

        User trainer = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));

        matchList = trainer.getTrainerMatches().stream()
                .map(match ->{

                    LocalDate birthdate = match.getMember().getBirthdate();
                    int age = Period.between(birthdate, LocalDate.now()).getYears();

                    return new MemberMatchListResponseDto(
                            match.getId(),
                            match.getMember().getId(),
                            match.getMember().getName(),
                            age,
                            match.getMember().getGender()
                    );

                }).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, matchList);
    }


    @Override
    public ResponseDto<MemberMatchResponseDto> findMatchTrainer(Long matchId) {
        MemberMatchResponseDto response = null;
        Match match  = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND + matchId));

        LocalDate birthdate = match.getMember().getBirthdate();
        int age = Period.between(birthdate, LocalDate.now()).getYears();



        if(match.getMember().getMember().getMemberForm() != null){
            MemberFormResponseDto memberFormResponseDto =  new MemberFormResponseDto(
                    match.getMember().getMember().getMemberId(),
                    match.getMember().getName(),
                    match.getMember().getMember().getMemberForm().getBodyForm(),
                    match.getMember().getMember().getMemberForm().getGoal(),
                    match.getMember().getMember().getMemberForm().getBmi(),
                    match.getMember().getMember().getMemberForm().getImprovedPart(),
                    match.getMember().getMember().getMemberForm().getPreferredDiet(),
                    match.getMember().getMember().getMemberForm().getSugarIntake(),
                    match.getMember().getMember().getMemberForm().getWaterIntake(),
                    match.getMember().getMember().getMemberForm().getHeight(),
                    match.getMember().getMember().getMemberForm().getWeight(),
                    match.getMember().getMember().getMemberForm().getWeightGoal(),
                    match.getMember().getMember().getMemberForm().getPhysicalLevel(),
                    match.getMember().getMember().getMemberForm().getExercisingProblem(),
                    match.getMember().getMember().getMemberForm().getPushupLevel(),
                    match.getMember().getMember().getMemberForm().getPullupLevel(),
                    match.getMember().getMember().getMemberForm().getExerciseFrequency(),
                    match.getMember().getMember().getMemberForm().getInvestableTime()
            );

            response = new MemberMatchResponseDto(
                    match.getMember().getName(),
                    age,
                    match.getMember().getGender(),
                    match.getMember().getPhone(),
                    match.getMember().getMember().getMemberAddress(),
                    memberFormResponseDto

            );
            return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
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


        User member  = userRepository.findById(match.getMember().getId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        member.setMemberMatch(null);
        matchRepository.delete(match);

        Subscription subscription = match.getMember().getMember().getSubscription();

        member.getMember().setSubscription(null);
        subscriptionRepository.delete(subscription);


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public Long findByUserId(Long userId, String role) {
        Optional<Match> match;

        if (role.equals("ROLE_MEMBER")) {
            match = matchRepository.findByMember_Id(userId);
        } else if (role.equals("ROLE_TRAINER")) {
            match = matchRepository.findByTrainer_Id(userId);
        } else {
            return null;
        }

        return match.map(Match::getId).orElse(null);
    }

}
