package com.battleship.gameengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.battleship")
@ComponentScan(basePackages = {"com.battleship"})
//@EnableJpaRepositories(basePackages = "com.battleship.lobby.repository")
//@EntityScan(basePackages = "com.battleship.lobby.entity")
@EnableEurekaClient
public class GameEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameEngineApplication.class, args);
    }
}
