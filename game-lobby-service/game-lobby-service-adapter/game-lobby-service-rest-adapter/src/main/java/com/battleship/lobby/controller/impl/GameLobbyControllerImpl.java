package com.battleship.lobby.controller.impl;

import com.battleship.lobby.controller.GameLobbyController;
import com.battleship.lobby.service.GameLobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class GameLobbyControllerImpl implements GameLobbyController {

    private final GameLobbyService gameLobbyService;

    @Override
    public ResponseEntity<?> createGameLobby(HttpServletRequest request) {
        return ResponseEntity.ok(gameLobbyService.createGameLobby(request.getUserPrincipal().getName()));
    }

    @Override
    public ResponseEntity<?> findAvailableGameLobby() {
        return ResponseEntity.ok(gameLobbyService.findAvailableGameLobby());
    }

    @Override
    public ResponseEntity<?> joinAvailableGameLobby(HttpServletRequest request, Integer gameLobbyJoin) {
        return ResponseEntity.ok(gameLobbyService.joinGameLobby(gameLobbyJoin, request.getUserPrincipal().getName()));
    }
}
