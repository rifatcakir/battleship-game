package com.battleship.rest.impl;

import com.battleship.rest.GameLobbyController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class GameLobbyControllerImpl implements GameLobbyController {

    @Override
    public ResponseEntity<?> createGameLobby(HttpServletRequest request) {
        return ResponseEntity.ok("HELLO GAME-ENGINE");
    }
}
