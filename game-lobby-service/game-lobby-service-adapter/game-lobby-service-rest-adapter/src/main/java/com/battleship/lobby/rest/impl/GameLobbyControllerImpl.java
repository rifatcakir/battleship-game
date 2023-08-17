package com.battleship.lobby.rest.impl;

import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.rest.model.GameLobbyAPIModel;
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
    public ResponseEntity<GameLobbyAPIModel> createGameLobby(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDomain(gameLobbyService.createGameLobby(request.getUserPrincipal().getName())));
    }

    @Override
    public ResponseEntity<List<GameLobbyAPIModel>> findAvailableGameLobby() {
        return ResponseEntity.ok(gameLobbyService.findAvailableGameLobby().stream().map(this::toDomain).toList());
    }

    @Override
    public ResponseEntity<GameLobbyAPIModel> joinAvailableGameLobby(HttpServletRequest request, UUID gameLobbyJoin) {
        return ResponseEntity.ok(toDomain(gameLobbyService.joinGameLobby(gameLobbyJoin, request.getUserPrincipal().getName())));
    }

    private GameLobbyAPIModel toDomain(GameLobbyModel gameLobbyModel) {
        return new GameLobbyAPIModel(gameLobbyModel.getGameLobbyId(), gameLobbyModel.getPlayer1Name(), gameLobbyModel.getPlayer2Name());
    }
}
