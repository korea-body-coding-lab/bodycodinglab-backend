package com.project.bcl_back.service.impl;

import com.project.bcl_back.common.constants.ResponseMessage;
import com.project.bcl_back.dto.ResponseDto;
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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public Mono<ResponseEntity<String>> sendVerifyEmail(SendEmailRequestDto dto) {
        boolean isEmailVerified = authService.checkEmail(dto.getEmail());

        if (!isEmailVerified) {
            return Mono.just(ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage.NO_EXIST_EMAIL));
        }

        return Mono.fromCallable(() -> {
            String token = jwtProvider.generateEmailValidToken(dto.getEmail());
            MimeMessage message = createMail(dto.getEmail(), token);
            javaMailSender.send(message);
            return ResponseEntity.ok(ResponseMessage.SUCCESS + " (인증 메일 발송)");
        }).onErrorResume(e -> Mono.just(
                ResponseEntity.badRequest().body(ResponseMessage.MAIL_SEND_FAIL))
        ).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<String>> verifyEmail(String token) {
        return Mono.fromCallable(() ->{
            String email = jwtProvider.getEmailFromJwt(token);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseMessage.SUCCESS + "(이메일 인증: " + email + ")\n" +
                            "token: " + token);
        }).onErrorResume(e -> Mono.just(
                ResponseEntity.badRequest().body(ResponseMessage.MAIL_AUTH_FAIL))
        ).subscribeOn(Schedulers.boundedElastic());
    }

    private MimeMessage createMail(String email, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Fit-Mate] 이메일 인증 링크 발송");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                
                <p>아래 이메일 인증 링크에 접속하여 인증을 완료해 주세요.</p>
                <a href="http://localhost:8080/api/v1/auth/verify?token=%s">여기를 클릭하여 인증 페이지에 접속해 주세요.</a>
                """.formatted(token);

        message.setText(body, "utf-8", "html");
        return message;
    }
}
