package com.battleship.lobby.service;

import com.battleship.lobby.model.GameLobbyModel;

import java.util.List;

public interface GameLobbyService {

    GameLobbyModel createGameLobby(String userName);

    List<GameLobbyModel> findAvailableGameLobby();

    GameLobbyModel joinGameLobby(Integer gameLobbyId, String userName);
}
