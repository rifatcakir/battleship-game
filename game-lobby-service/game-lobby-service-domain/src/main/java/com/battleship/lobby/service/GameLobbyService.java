package com.battleship.lobby.service;

import com.battleship.lobby.model.GameLobbyModel;

import java.util.List;
import java.util.UUID;

public interface GameLobbyService {

    GameLobbyModel createGameLobby(String userName);

    List<GameLobbyModel> findAvailableGameLobby();

    GameLobbyModel joinGameLobby(UUID gameLobbyId, String userName);
}
