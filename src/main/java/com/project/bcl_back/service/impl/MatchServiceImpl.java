//package com.project.bcl_back.service.impl;
//
//import com.project.bcl_back.common.constants.ResponseCode;
//import com.project.bcl_back.common.constants.ResponseMessage;
//import com.project.bcl_back.dto.ResponseDto;
//import com.project.bcl_back.dto.match.response.MemberMatchListResponseDto;
//import com.project.bcl_back.dto.match.response.TrainerMatchResponseDto;
//import com.project.bcl_back.entity.User;
//import com.project.bcl_back.repository.MatchRepository;
//import com.project.bcl_back.repository.UserRepository;
//import com.project.bcl_back.service.MatchService;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.Year;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class MatchServiceImpl implements MatchService {
//    private final MatchRepository matchRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public ResponseDto<TrainerMatchResponseDto> findMatchTrainer(Long userId) {
//        TrainerMatchResponseDto response = null;
//
//        User member = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));
//
//        response = new TrainerMatchResponseDto(
//                member.getMemberMatch().getTrainer().getTrainerInfo().getId(),
//                member.getMemberMatch().getTrainer().getUsername(),
//                member.getMemberMatch().getTrainer().getTrainerInfo().getTrainerLicense().stream().map(
//                        TrainerLicense::getLicenseName
//                ).collect(Collectors.toList())
//        );
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
//    }
//
//    @Override
//    public ResponseDto<List<MemberMatchListResponseDto>> findMatchMemberList(Long userId) {
//        List<MemberMatchListResponseDto> matchList = null;
//
//        User trainer = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND + userId));
//
//        matchList = trainer.getTrainerMatchs().stream()
//                .map(match ->{
//
//                    LocalDate birthdate = match.getMember().getBirthdate();
//                    int age = Year.now().getValue() - birthdate.getYear();
//
//                    return new MemberMatchListResponseDto(
//                            match.getMember().getName(),
//                            age,
//                            match.getMember().getGender()
//                    );
//
//                }).collect(Collectors.toList());
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, matchList);
//    }
//}
