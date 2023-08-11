package com.battleship.gameengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class GameEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameEngineApplication.class, args);
    }
}
