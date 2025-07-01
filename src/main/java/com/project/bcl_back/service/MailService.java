package com.project.bcl_back.service;

import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.project.bcl_back.dto.auth.request.SendResetPasswordEmailRequestDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

public interface MailService {
    ResponseDto<Void> sendResetPasswordEmail(@Valid SendResetPasswordEmailRequestDto dto) throws MessagingException;
    ResponseDto<Void> verifyEmail(String token);
    void sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto) throws MessagingException;
}
