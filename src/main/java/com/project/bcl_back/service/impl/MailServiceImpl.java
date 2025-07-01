package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.project.bcl_back.dto.auth.request.SendResetPasswordEmailRequestDto;
import com.project.bcl_back.provider.JwtProvider;
import com.project.bcl_back.service.AuthService;
import com.project.bcl_back.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public ResponseDto<Void> sendResetPasswordEmail(SendResetPasswordEmailRequestDto dto) throws MessagingException {
        boolean isEmailVerified = authService.checkEmail(dto.getEmail());

        if (!isEmailVerified) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_EMAIL, ResponseMessage.NO_EXIST_EMAIL);
        }

        String token = jwtProvider.generateResetPasswordJwtToken(dto.getEmail());
        MimeMessage message = createVerifyMail(dto.getEmail(), token);
        javaMailSender.send(message);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS + " (인증 메일 발송)");
    }

    @Override
    public ResponseDto<Void> verifyEmail(String token) {
        if (token == null) {
            return ResponseDto.fail(ResponseCode.MISSING_TOKEN, ResponseMessage.MISSING_TOKEN);
        }

        String email = jwtProvider.getEmailFromJwt(token);
        boolean isEmailVerified = authService.checkEmail(email);

        if (!isEmailVerified) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_EMAIL, ResponseMessage.NO_EXIST_EMAIL);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS + " (메일 인증 성공)");
    }

    @Override
    public void sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto) throws MessagingException {
        boolean isEmailVerified = authService.checkEmail(dto.getEmail());

        if (!isEmailVerified) {
            throw new IllegalStateException(ResponseMessage.NO_EXIST_EMAIL);
        }

        if (dto.getStatus().equals(TrainerStatus.APPROVE)) {
            MimeMessage message = createTrainerApproveMail(dto.getEmail());
            javaMailSender.send(message);
        } else if (dto.getStatus().equals(TrainerStatus.REJECT)) {
            MimeMessage message = createTrainerReapplyMail(dto.getEmail(), dto.getChangeReason());
            javaMailSender.send(message);
        } else {
            MimeMessage message = createTrainerPendingMail(dto.getEmail());
            javaMailSender.send(message);
        }
    }

    private MimeMessage createVerifyMail(String email, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Fit-Mate] 비밀번호 재설정 링크 발송");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                <p>아래 비밀번호 재설정 링크에 접속하여 인증을 완료해 주세요.</p>
                <a href="http://localhost:5173/password/reset?token=%s">여기를 클릭하여 설정 페이지에 접속해 주세요.</a>
                <p>감사합니다.</p>
                """.formatted(token);

        message.setText(body, "utf-8", "html");
        return message;
    }

    private MimeMessage createTrainerReapplyMail(String email, String changeReason) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Fit-Mate] 트레이너 재신청 요청");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                <p>트레이너 가입이 거부되어 재신청 절차를 안내드립니다.</p>
                
                <ol>
                  <li><strong>거부 사유 확인</strong></li>
                  <li><strong>서류 보완</strong></li>
                  <li><strong>재신청</strong></li>
                  <li><strong>가입 승인 대기</strong></li>
                </ol>
                
                <p>
                  [ 거부 사유 ] <strong style="color: red;">%s</strong>
                </p>
                
                <p>홈페이지에 방문하여 재신청 해주시길 바랍니다.</p>
                <p>감사합니다.</p>
                """.formatted(changeReason);

        message.setText(body, "utf-8", "html");
        return message;
    }

    private MimeMessage createTrainerApproveMail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Fit-Mate] 트레이너 승인 안내");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                <p>
                    트레이너 가입이 승인되었습니다.<br />
                    홈페이지에 방문하시면, 서비스 이용이 가능합니다. <br />
                </p>
                <p>감사합니다.</p>
                """;

        message.setText(body, "utf-8", "html");
        return message;
    }

    private MimeMessage createTrainerPendingMail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Fit-Mate] 트레이너 서비스 안내");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                <p>
                    트레이너 신청이 완료되었습니다.<br />
                    현재 관리자 승인 검토 단계입니다.<br />
                    관리자의 승인 이후, 서비스 이용이 가능하오니 참고 바랍니다.<br />
                </p>
                <p>감사합니다.</p>
                """;

        message.setText(body, "utf-8", "html");
        return message;
    }
}
