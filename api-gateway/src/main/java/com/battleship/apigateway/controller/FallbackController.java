package com.battleship.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/authServiceFallBack")
    public String authServiceFallback() {
        return "Auth Service is down!";
    }

    @GetMapping("/gameEngineServiceFallBack")
    public String gameEngineServiceFallBack() {
        return "Game Engine Service is down!";
    }

    @GetMapping("/gameLobbyServiceFallBack")
    public String gameLobbyServiceFallBack() {
        return "Game Lobby Service is down!";
    }

}
