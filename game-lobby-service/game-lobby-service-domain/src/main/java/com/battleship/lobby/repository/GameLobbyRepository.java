package com.battleship.lobby.repository;

import com.battleship.lobby.model.GameLobbyModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameLobbyRepository {
    GameLobbyModel saveGameLobby(GameLobbyModel gameLobbyModel);

    List<GameLobbyModel> findAvailableGameLobby();

    Optional<GameLobbyModel> findGameLobbyById(UUID gameLobbyId);
}
