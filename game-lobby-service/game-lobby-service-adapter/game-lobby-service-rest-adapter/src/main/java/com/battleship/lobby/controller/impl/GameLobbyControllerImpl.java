package com.battleship.lobby.controller.impl;

import com.battleship.lobby.controller.GameLobbyController;
import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.service.GameLobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GameLobbyControllerImpl implements GameLobbyController {

    private final GameLobbyService gameLobbyService;

    @Override
    public ResponseEntity<GameLobbyModel> createGameLobby(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameLobbyService.createGameLobby(request.getUserPrincipal().getName()));
    }

    @Override
    public ResponseEntity<List<GameLobbyModel>> findAvailableGameLobby() {
        return ResponseEntity.ok(gameLobbyService.findAvailableGameLobby());
    }

    @Override
    public ResponseEntity<GameLobbyModel> joinAvailableGameLobby(HttpServletRequest request, UUID gameLobbyJoin) {
        return ResponseEntity.ok(gameLobbyService.joinGameLobby(gameLobbyJoin, request.getUserPrincipal().getName()));
    }
}
