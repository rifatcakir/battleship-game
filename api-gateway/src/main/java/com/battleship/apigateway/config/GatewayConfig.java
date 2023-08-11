package com.battleship.apigateway.config;

import com.battleship.apigateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r.path("/authenticate/**").filters(f -> f.filter(filter)).uri("lb://AUTH-SERVICE"))
                .route("GAME-LOBBY-SERVICE", r -> r.path("/game-lobby/**").filters(f -> f.filter(filter)).uri("lb://GAME-LOBBY-SERVICE"))
                .route("GAME-ENGINE-SERVICE", r -> r.path("/game-engine/**").filters(f -> f.filter(filter)).uri("lb://GAME-ENGINE-SERVICE"))
                .route("openapi", r -> r.path("/v3/api-docs/**").filters(f -> f.filter(filter)).uri("http://localhost:8080"))
                .build();
    }
}
