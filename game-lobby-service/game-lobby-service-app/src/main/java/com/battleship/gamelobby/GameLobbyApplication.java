package com.battleship.gamelobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.battleship")
@ConfigurationPropertiesScan("com.battleship")
@EnableEurekaClient
public class GameLobbyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameLobbyApplication.class, args);
    }
}