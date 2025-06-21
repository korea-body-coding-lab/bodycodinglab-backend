package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseCode;
import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.dto.ResponseDto;
import com.project.bcl_back.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.project.bcl_back.dto.auth.request.SendEmailRequestDto;
import com.project.bcl_back.provider.JwtProvider;
import com.project.bcl_back.service.AuthService;
import com.project.bcl_back.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> sendVerifyEmail(SendEmailRequestDto dto) {
        boolean isEmailVerified = authService.checkEmail(dto.getEmail());

        if (!isEmailVerified) {
            return Mono.just(
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ResponseDto.fail(ResponseCode.NO_EXIST_EMAIL, ResponseMessage.NO_EXIST_EMAIL))
            );
        }

        return Mono.fromCallable(() -> {
            String token = jwtProvider.generateEmailValidToken(dto.getEmail());
            MimeMessage message = createVerifyMail(dto.getEmail(), token);
            javaMailSender.send(message);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.<String>success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS + " (인증 메일 발송)"));
        }).onErrorResume(e -> Mono.just(
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.fail(ResponseCode.MAIL_SEND_FAIL, ResponseMessage.MAIL_SEND_FAIL)))
        ).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> verifyEmail(String token) {
        if (token == null) {
            return Mono.just(
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN))
            );
        }
        return Mono.fromCallable(() -> {
            String email = jwtProvider.getEmailFromJwt(token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.<String>success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS + " (메일 인증 성공)"));
        }).onErrorResume(e -> Mono.just(
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.fail(ResponseCode.MAIL_AUTH_FAIL, ResponseMessage.MAIL_AUTH_FAIL)))
        ).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto) {
        boolean isEmailVerified = authService.checkEmail(dto.getEmail());

        if (!isEmailVerified) {
            return Mono.just(
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ResponseDto.fail(ResponseCode.NO_EXIST_EMAIL, ResponseMessage.NO_EXIST_EMAIL))
            );
        }

        return Mono.fromCallable(() -> {
            if (dto.getStatus().equals(TrainerStatus.APPROVE)) {
                MimeMessage message = createTrainerApproveMail(dto.getEmail());
                javaMailSender.send(message);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.<String>success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS + " (승인 메일 발송)"));
            } else {
                MimeMessage message = createTrainerReapplyMail(dto.getEmail(), dto.getChangeReason());
                javaMailSender.send(message);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.<String>success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS + " (재신청 메일 발송)"));
            }
        }).onErrorResume(e -> Mono.just(
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.fail(ResponseCode.MAIL_SEND_FAIL, ResponseMessage.MAIL_SEND_FAIL)))
        ).subscribeOn(Schedulers.boundedElastic());
    }

    private MimeMessage createVerifyMail(String email, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Fit-Mate] 비밀번호 재설정 링크 발송");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                <br />
                <p>아래 비밀번호 재설정 링크에 접속하여 인증을 완료해 주세요.</p>
                <a href="http://localhost:5173/auth/reset-password/setting?token=%s">여기를 클릭하여 설정 페이지에 접속해 주세요.</a>
                <br />
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
                <br />
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                <br />
                <p>트레이너 가입이 거부되어 재신청 절차 안내드립니다.</p>
                <b>1. 거부 사유 확인</b><br />
                <b>2. 서류 보완</b></br>
                <b>3. 아래 링크에 접속하여 재신청</b><br />
                <b>4. 가입 승인 대기</b><br /></p>
                <br />
                <p>거부 사유: <strong>%s</strong><br />
                <a href="http://localhost:5173/api/v1/auth/trainer-reapply?email=%s">여기를 클릭하여 재신청 페이지에 접속해 주세요.</a><br /></p>
                <br />
                <p>감사합니다.</p>
                """.formatted(changeReason, email);

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
                <br />
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                <br />
                <p>트레이너 가입이 승인되었습니다.<br />
                홈페이지에 방문하시면, 서비스 이용이 가능합니다. <br />
                이용 중 불편함이 있으시면 고객센터로 연락 바랍니다.</p>
                <br />
                <p>감사합니다.</p>
                """;

        message.setText(body, "utf-8", "html");
        return message;
    }
}
