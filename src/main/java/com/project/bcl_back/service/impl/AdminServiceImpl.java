package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.user.UserRole;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.response.GetAllTrainersResponseDto;
import com.project.bcl_back.entity.Role;
import com.project.bcl_back.entity.User;
import com.project.bcl_back.repository.RoleRepository;
import com.project.bcl_back.repository.TrainerInfoRepository;
import com.project.bcl_back.repository.UserRepository;
import com.project.bcl_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TrainerInfoRepository trainerInfoRepository;

    @Override
    public ResponseDto<List<GetAllTrainersResponseDto>> getAllTrainers() {
        List<GetAllTrainersResponseDto> data = null;

        Role role = roleRepository.findByName(UserRole.TRAINER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.TRAINER).build()));

        data = userRepository.findByRole(role).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    private GetAllTrainersResponseDto toDto(User user) {
        return GetAllTrainersResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .age(Year.now().getValue() - user.getBirthdate().getYear())
                .gender(user.getGender())
                .status(user.getTrainerInfo().getStatus())
                .build();
    }
}
