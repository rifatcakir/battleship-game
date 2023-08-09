package com.battleship.gamelobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.battleship")
@ComponentScan(basePackages = {"com.battleship"})
@ConfigurationPropertiesScan("com.battleship")
@EnableJpaRepositories(basePackages = "com.battleship.lobby.repository")
@EntityScan(basePackages = "com.battleship.lobby.entity")
@EnableEurekaClient
public class GameLobbyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameLobbyApplication.class, args);
    }
}