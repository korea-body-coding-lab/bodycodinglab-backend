package com.project.bcl_back.handler;

import com.project.bcl_back.provider.JwtProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Component
public class EmailHandler {
    private final JwtProvider jwtProvider;

    public EmailHandler(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
        String token = request.queryParam("token").orElse(null);

        if (token == null) {
            return ServerResponse
                    .badRequest()
                    .bodyValue("토큰이 누락되었습니다.");
        }

        return Mono.fromCallable(() -> {
                    String email = jwtProvider.getEmailFromJwt(token);
                    URI redirectUri = URI.create("http://localhost:5173/api/v1/auth/reset-password/setting?email=" + email);
                    return redirectUri;
                })
                .flatMap(uri -> ServerResponse.temporaryRedirect(uri).build())
                .onErrorResume(e -> ServerResponse
                        .badRequest()
                        .bodyValue("이메일 인증 실패: " + e.getMessage()))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
