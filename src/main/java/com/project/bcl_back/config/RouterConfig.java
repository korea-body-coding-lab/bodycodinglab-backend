package com.project.bcl_back.config;

import com.project.bcl_back.handler.EmailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(EmailHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("http://localhost:8080/api/v1/auth/verify"), handler::verifyEmail);
    }
}