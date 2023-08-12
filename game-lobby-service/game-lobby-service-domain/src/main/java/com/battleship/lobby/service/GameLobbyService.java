package com.battleship.lobby.service;

import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.model.PlayerInfo;

import java.util.List;

public interface GameLobbyService {

    GameLobbyModel createGameLobby(String userName);

    List<GameLobbyModel> findAvailableGameLobby();

    GameLobbyModel joinGameLobby(Integer gameLobbyId, PlayerInfo playerInfo);
}
